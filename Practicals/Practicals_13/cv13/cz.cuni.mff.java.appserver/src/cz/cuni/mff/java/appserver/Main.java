package cz.cuni.mff.java.appserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class Main {

    private static final int PORT = 8888;

    private static final Map<String, ClassInfo> apps = new HashMap<>();

    private static class ClassInfo {

        class MethodInfo {
            String name;
            int argsNo;
            String contentType = "Content-Type: text/plain";
            Method method;

            MethodInfo(Method m) {
                method = m;
                argsNo = m.getParameterCount();
                if (m.isAnnotationPresent(Return.class)) {
                    contentType = "Content-Type: " + m.getAnnotation(Return.class).mime();
                }
            }
        }

        int methodsNo;
        List<MethodInfo> methods;
        Class<?> clazz;

        ClassInfo(Class<?> clazz) {
            this.clazz = clazz;
            Method[] rawMethods = clazz.getMethods();
            methods = Arrays.stream(rawMethods).filter(m -> Modifier.isStatic(m.getModifiers())).map(MethodInfo::new).collect(Collectors.toList());
            methodsNo = methods.size();
        }

        MethodInfo getMethod(String name) {
            for (MethodInfo m: methods) {
                if (m.method.getName().equals(name)) {
                    return m;
                }
            }
            return null;
        }
    }

    public static void main(String[] args) {

        for (String arg: args) {
            try {
                Class<?> clazz = Class.forName(arg);
                apps.put(clazz.getSimpleName(), new ClassInfo(clazz));
            } catch (ClassNotFoundException e) {
                Logger.getLogger(Main.class.getName()).log(Level.WARNING, ()-> "Class " + arg + " not found.");
            }
        }

        try (ServerSocket ss = new ServerSocket(PORT)) {
            while (true) {
                Socket s = ss.accept();
                Thread t = new Thread(new Servant(s));
                t.start();
            }
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, "IOException occurred", ex);
        }
    }

    private static class Servant implements Runnable {
        private final Socket s;

        public Servant(Socket socket) {
            this.s = socket;
        }

        @Override
        public void run() {
            try (BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
                 OutputStream os = s.getOutputStream()) {
                String line = br.readLine(); // reading the line with GET...
                String[] firstLine = line.split(" ");
                if (!firstLine[0].equals("GET")) {
                    Logger.getLogger(Main.class.getName()).log(Level.WARNING, "Unsupported operation");
                    String resp = "HTTP/1.0 405 Method Not Allowed\nAllow: GET\n\n";
                    os.write(resp.getBytes());
                    return;
                }
                do {
                    line = br.readLine(); // skipping the rest of the request
                } while (!line.isEmpty());

                String pathString = firstLine[1];

                if (pathString.startsWith("/")) {
                    pathString = pathString.substring(1);
                }

                String[] pathElements = pathString.split("\\?");

                String[] names = pathElements[0].split("/");
                if (names.length != 2) {
                    Logger.getLogger(Main.class.getName()).log(Level.WARNING, pathElements[0] + " Bad app");
                    String resp = "HTTP/1.0 404 Not Found\n\nApp Not Found\n";
                    os.write(resp.getBytes());
                    return;
                }
                ClassInfo app = apps.get(names[0]);
                if (app == null) {
                    Logger.getLogger(Main.class.getName()).log(Level.WARNING, pathElements[0] + " is unknown app");
                    String resp = "HTTP/1.0 404 Not Found\n\nApp Not Found\n";
                    os.write(resp.getBytes());
                    return;
                }

                ClassInfo.MethodInfo method = app.getMethod(names[1]);
                if (method == null) {
                    Logger.getLogger(Main.class.getName()).log(Level.WARNING, names[1] + " unknown method");
                    String resp = "HTTP/1.0 404 Not Found\n\nMethod Not Found\n";
                    os.write(resp.getBytes());
                    return;
                }

                Object[] actualArgs;

                if (pathElements.length == 1) {
                    if (method.argsNo == 0) {
                        actualArgs = new Object[0];
                    } else {
                        Logger.getLogger(Main.class.getName()).log(Level.WARNING, "Bad number of arguments");
                        String resp = "HTTP/1.0 404 Not Found\n\nBad arguments\n";
                        os.write(resp.getBytes());
                        return;
                    }
                } else {
                    String[] args = pathElements[1].split("&");
                    actualArgs = new Object[args.length];
                    int i = 0;
                    for (String arg: args) {
                        String[] pair = arg.split("=");
                        if (pair.length != 2) {
                            Logger.getLogger(Main.class.getName()).log(Level.WARNING, "Bad format of argument: " + pathElements[1]);
                            String resp = "HTTP/1.0 404 Not Found\n\nBad argument\n";
                            os.write(resp.getBytes());
                            return;
                        }
                        actualArgs[i++] = pair[1]; // WARNING - works with String args only and they must be in the correct order
                    }
                }

                try {
                    String result = (String) method.method.invoke(null, actualArgs);  // add catch for ClassCastException

                    Logger.getLogger(Main.class.getName()).log(Level.INFO, "Processed " + pathString);
                    String resp = "HTTP/1.0 200 OK\n" + method.contentType + "\n\n" + result;
                    os.write(resp.getBytes());

                } catch (IllegalAccessException | InvocationTargetException e) {
                    Logger.getLogger(Main.class.getName()).log(Level.WARNING, pathElements[0] + " unknown method");
                    String resp = "HTTP/1.0 404 Not Found\n\nCannot call method\n";
                    os.write(resp.getBytes());
                    return;
                }

            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, "IOException occurred", ex);
            } finally {
                try {
                    s.close();
                } catch (IOException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, "IOException occurred", ex);
                }
            }
        }
    }
}
