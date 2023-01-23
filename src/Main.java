import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    new LoginWindow();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });




    }
}