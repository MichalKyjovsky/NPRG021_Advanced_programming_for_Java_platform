package cz.cuni.mff.java.plugins;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Plug {
    public static void main(String[] args) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            Loader.loadPlugins(Class.forName(br.readLine()), br.readLine(), br.readLine()).forEach(System.out::println);
        } catch (IOException | ClassNotFoundException ioException) {
            ioException.printStackTrace();
        }
    }
}
