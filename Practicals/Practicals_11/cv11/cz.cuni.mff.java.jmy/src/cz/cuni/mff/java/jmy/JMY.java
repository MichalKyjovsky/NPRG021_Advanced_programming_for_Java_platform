package cz.cuni.mff.java.jmy;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetAddress;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JMY {
    public static <T> T connect(Class<T> clazz, String name, String host, int port) {
        try (Socket s = new Socket(InetAddress.getByName(host), port);
                ObjectOutputStream os = new ObjectOutputStream(s.getOutputStream());
                ObjectInputStream is = new ObjectInputStream(s.getInputStream())) {
            os.writeObject(new JMYMessageIn(name));
            JMYMessageOut msg = (JMYMessageOut) is.readObject();
            if (msg.getType() == JMYMessageOutType.NOOBJECT) {
                return null;
            } else {
                return clazz.cast(Proxy.newProxyInstance(clazz.getClassLoader(), new Class[] { clazz }, new JMYHandler(host, name, port)));
            }
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(JMY.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException(ex);
        }                        
    }   
    
    private static class JMYHandler implements InvocationHandler {
        private final String host;
        private final String name;
        private final int port;

        JMYHandler(String host, String name, int port) {
            this.host = host;
            this.name = name;
            this.port = port;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            try (Socket s = new Socket(InetAddress.getByName(host), port);
                ObjectOutputStream os = new ObjectOutputStream(s.getOutputStream());
                ObjectInputStream is = new ObjectInputStream(s.getInputStream())) {

                os.writeObject(new JMYMessageIn(name, method.getName(), method.getParameterTypes(), args));
                JMYMessageOut msg = (JMYMessageOut) is.readObject();
                
                switch (msg.getType()) {
                    case EXCEPTION:
                        throw (Exception) msg.getResult();
                    case NOMETHOD:
                        throw new RuntimeException("No method");
                    case NOOBJECT:
                        throw new RuntimeException("No object");
                    default:
                        return msg.getResult();
                }
            } catch (IOException | ClassNotFoundException ex) {
                Logger.getLogger(JMY.class.getName()).log(Level.SEVERE, null, ex);
                throw new RuntimeException(ex);
            }
        }
    }
}