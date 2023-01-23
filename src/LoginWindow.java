import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class LoginWindow extends JFrame {

    private JTextField name;
    private JButton login;
    private Socket socket;
    private BufferedReader input;
    private PrintStream output;
    private String message;


    public LoginWindow() throws IOException {
        setSize(200, 100);
        setLayout(new GridLayout(2,1));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        socket = new Socket("localhost", 5000);
        input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        output = new PrintStream(socket.getOutputStream());


        name = new JTextField();
        login = new JButton("loguj");

        add(name);
        add(login);

        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                Podaje swoje imie

                message = name.getText();
                output.println(message);
                output.flush();

//                Odbieram komunikat
                try {
                    message = input.readLine();
                    System.out.println(message);
                    if (message.equals("EXIST")) {
                        output.close();
                        input.close();
                        socket.close();
                        setVisible(false);
                        dispose();
                    } else if (message.equals("ADDED")) {
                        setVisible(false);
                        dispose();
                        new MainWindow(socket);

                    }
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });

        setVisible(true);
    }
}
