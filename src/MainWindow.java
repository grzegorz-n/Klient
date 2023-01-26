import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

public class MainWindow extends JFrame {

    private PrintStream output;
    private JTextField message;
    private JTextArea textArea;
    private JButton send;
    private JPanel panel;
    private Input input;
    private JList<String> userList;

    public MainWindow(Socket socket) {
        setSize(400, 600);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                output.println("EXIT");
                output.flush();
                input.ending();
            }
        });

        textArea = new JTextArea(15,30);
        message = new JTextField(30);
        send = new JButton("Wyślij");
        panel = new JPanel();
        userList = new JList<>();

        add(userList, BorderLayout.LINE_END);
        add(new JScrollPane(textArea), BorderLayout.CENTER);
        panel.add(message);
        panel.add(send);
        add(panel, BorderLayout.PAGE_END);

        send.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea.append("[JA] " + message.getText() + "\n");
                output.println("[" + userList.getSelectedValue().length() + "] " + "[" + userList.getSelectedValue() + "] " + message.getText());
                output.flush();
                message.setText("");
            }
        });


        setVisible(true);

        try {
            input = new Input(socket, textArea, userList);
            input.start();
            output = new PrintStream(socket.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
