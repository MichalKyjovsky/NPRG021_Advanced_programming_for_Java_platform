package RemoteClassLoaderTest;

import java.net.*;
import java.io.*;

public class URLClassLoader extends ClassLoader {

    private String url;

    public URLClassLoader(String url) {
        this.url = url;
    }

    @Override
    public Class findClass(String name) throws ClassNotFoundException {
        byte[] b;
        b = loadClassData(name);
        Class ret = defineClass(name, b, 0, b.length);
        return ret;
    }

    private byte[] loadClassData(String name) throws ClassNotFoundException {
        String path = name.replaceAll("\\.", "/") + ".class";

        URL rurl = null;

        try {
            rurl = new URL(url + path);
        } catch (MalformedURLException e) {
            throw new ClassNotFoundException("MalformedURLException: " + e.getMessage());
        }

        try {
            URLConnection con = rurl.openConnection();
            InputStream is = con.getInputStream();
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            int a = is.read();
            while (a != -1) {
                bo.write(a);
                a = is.read();
            }
            is.close();
            byte[] ar = bo.toByteArray();
            bo.close();
            return ar;
        } catch (IOException e) {
            throw new ClassNotFoundException("IOException: " + e.getMessage());
        }
    }
}
