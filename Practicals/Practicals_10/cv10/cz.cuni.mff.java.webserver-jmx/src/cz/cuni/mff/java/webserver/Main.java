package cz.cuni.mff.java.webserver;

import javax.management.*;
import javax.script.*;
import java.io.*;
import java.lang.management.ManagementFactory;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    private static Path webDir;
    private static int port;
    volatile private static State state;

    private static Map<String, ScriptEngine> scripts = new HashMap<>();

    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("2 parameters required - port and directory");
            System.exit(1);
        }
        try {
            port = Integer.parseInt(args[0]);
        } catch (NumberFormatException ex) {
            System.out.println("Given port is not a number");
            System.exit(1);
        }
        webDir = Path.of(args[1]);
        if (!Files.isDirectory(webDir)) {
            System.out.println("Given path is not a directory");
            System.exit(1);
        }

        try {
            MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
            ObjectName name = new ObjectName("cz.cuni.mff.java.webserver:type=Server");
            ManagementImpl mbean = new ManagementImpl();
            mbs.registerMBean(mbean, name);
        } catch (MalformedObjectNameException | InstanceAlreadyExistsException | MBeanRegistrationException | NotCompliantMBeanException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, "Cannot create/register MBean", ex);
        }

        initializeScriptEngines();
        state = State.RUNNING;

        try (ServerSocket ss = new ServerSocket(port)) {
            ss.setSoTimeout(10);
            while (true) {
                if (state == State.RUNNING) {
                    try {
                        Socket s = ss.accept();
                        Logger.getLogger(Main.class.getName()).log(Level.INFO, "Accepting connection");
                        Thread t = new Thread(new Servant(s));
                        t.start();
                    } catch (SocketTimeoutException ex) {}
                } else {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException ex) {}
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, "IOException occurred", ex);
        }
    }

    private static void initializeScriptEngines() {
        ScriptEngineManager sem = new ScriptEngineManager();
        sem.getEngineFactories().forEach(sef -> sef.getExtensions().forEach(ext -> scripts.put(ext, sef.getScriptEngine())));
    }

    private static ScriptEngine getEngineForPath(Path p) {
        String ps = p.toString();
        for (String ext : scripts.keySet()) {
            if (ps.endsWith("." + ext)) {
                return scripts.get(ext);
            }
        }
        return null;
    }

    private static class Servant implements Runnable {

        private final Socket s;

        public Servant(Socket s) {
            this.s = s;
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
                pathString = pathString.replaceAll("/", File.separator);
                Path path = webDir.resolve(Path.of(pathString));

                Logger.getLogger(Main.class.getName()).log(Level.INFO, "File {0}", path.toString());
                if (Files.exists(path)) {
                    Logger.getLogger(Main.class.getName()).log(Level.INFO, "File found");

                    ScriptEngine eng = getEngineForPath(path);
                    if (eng == null) {

                        String resp = "HTTP/1.0 200 OK\n\n";
                        os.write(resp.getBytes());
                        try (InputStream is = Files.newInputStream(path)) {
                            int b;
                            while ((b = is.read()) != -1) {
                                os.write(b);
                            }
                        }
                    } else {
                        try (BufferedReader re = Files.newBufferedReader(path)) {
                            ScriptContext ctx = new SimpleScriptContext();
                            ByteArrayOutputStream bos = new ByteArrayOutputStream();
                            ctx.setWriter(new OutputStreamWriter(bos));
                            eng.eval(re, ctx);
                            String resp = "HTTP/1.0 200 OK\n\n";
                            os.write(resp.getBytes());
                            os.write(bos.toByteArray());
                        } catch (ScriptException ex) {
                            Logger.getLogger(Main.class.getName()).log(Level.WARNING, "ScriptException", ex);
                            String resp = "HTTP/1.0 500 Internal Server Error\n\nInternal Error\n";
                            os.write(resp.getBytes());
                            return;
                        }
                    }
                } else {
                    Logger.getLogger(Main.class.getName()).log(Level.WARNING, "File Not Found");
                    String resp = "HTTP/1.0 404 Not Found\n\nNotFound\n";
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

    private static enum State {
        RUNNING, PAUSED
    };

    private static class ManagementImpl implements Management {

        @Override
        public void shutdown() {
            System.exit(0);
        }

        @Override
        public String getDirectory() {
            return webDir.toString();
        }

        @Override
        public void setDirectory(String dir) {
            Path p = Path.of(dir);
            if ((!Files.exists(webDir)) || (!Files.isDirectory(webDir))) {
                throw new IllegalArgumentException("Bad directory");
            }
            webDir = p;
        }

        @Override
        public String getState() {
            return state.toString();
        }

        @Override
        public void pause() {
            state = State.PAUSED;
        }

        @Override
        public void resume() {
            state = State.RUNNING;
        }
    }
}
