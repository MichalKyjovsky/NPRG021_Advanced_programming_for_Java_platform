package cz.cuni.mff.java.jmy;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JMYServer {

    private final int port;
    private final Map<String, Object> objs = new HashMap<>();

    public JMYServer(int port) {
        this.port = port;
        Thread mainThread = new Thread(this::runMainThread);
        mainThread.setDaemon(true);
        mainThread.start();
    }

    public void register(String name, Object obj) throws JMYException {
        checkAnnotation(obj);
        objs.put(name, obj);
    }

    private void checkAnnotation(Object obj) throws JMYException {
        Class<?> clazz = obj.getClass();
        for (Class<?> iclazz : clazz.getInterfaces()) {
            if (iclazz.getAnnotation(Mgmt.class) != null) {
                return;
            }
        }
        throw new JMYException("No interface with @Mgmt annotation");
    }

    private void runMainThread() {
        try (ServerSocket ss = new ServerSocket(port)) {
            while (true) {
                Socket s = ss.accept();
                Thread t = new Thread(new ServerConnection(s));
                t.start();
            }
        } catch (IOException ex) {
            Logger.getLogger(JMYServer.class.getName()).log(Level.SEVERE, "IOException", ex);
            throw new RuntimeException();
        }
    }

    private class ServerConnection implements Runnable {

        private final Socket s;

        public ServerConnection(Socket s) {
            this.s = s;
        }

        @Override
        public void run() {
            try (ObjectInputStream is = new ObjectInputStream(s.getInputStream());
                 ObjectOutputStream os = new ObjectOutputStream(s.getOutputStream())) {
                try {
                    Object o = is.readObject();
                    JMYMessageIn msg = (JMYMessageIn) o;
                    Object bean = objs.get(msg.getObjectName());
                    if (bean == null) {
                        Logger.getLogger(JMYServer.class.getName()).log(Level.INFO, () -> "No object with the name " + msg.getObjectName());
                        os.writeObject(new JMYMessageOut(JMYMessageOutType.NOOBJECT));
                    } else if (msg.getType() == JMYMessageInType.CALL) {
                        try {
                            Method m = bean.getClass().getMethod(msg.getMethodName(), msg.getArgTypes());
                            try {
                                Logger.getLogger(JMYServer.class.getName()).log(Level.INFO, () -> "Calling method " + m.toString());
                                Object result = m.invoke(bean, msg.getArgs());
                                os.writeObject(new JMYMessageOut(JMYMessageOutType.OK, result));
                            } catch (Exception ex) {
                                os.writeObject(new JMYMessageOut(ex));
                            }
                        } catch (NoSuchMethodException ex) {
                            os.writeObject(new JMYMessageOut(JMYMessageOutType.NOMETHOD));
                        }
                    } else {  // type is QUERY
                        os.writeObject(new JMYMessageOut(JMYMessageOutType.OK));
                    }
                } catch (ClassNotFoundException | ClassCastException ex) {
                    Logger.getLogger(JMYServer.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (IOException ex) {
                Logger.getLogger(JMYServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}

