import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Input extends Thread {
    private Socket socket;
    private BufferedReader input;
    private JTextArea textArea;
    private JList<String> users;
    private boolean end;
    private DefaultListModel<String> userList;

    public Input(Socket socket, JTextArea textArea, JList<String> users) {
        this.socket = socket;
        this.textArea = textArea;
        this.end = true;
        this.users = users;
    }
    public void run() {
        try {
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while (end) {
                String message = input.readLine();
                if (message.equals("EXIT")) {
                    Thread.sleep(100);
                    break;
                } else if (message.substring(0, 6).equals("[LIST]")) {
                    userList = new DefaultListModel<>();
                    userList.add(0, "Wszyscy");
                    message = message.substring(6);
                    String user;
                    int userCount = 1;
                    for (int i = 0; i < message.length(); i++) {
                        if (message.charAt(i)==']') {
                            user = message.substring(1, i);
                            userList.add(userCount, user);
                            message = message.substring(i+1);
                            i=0;
                            userCount++;
                        }
                    }
                    users.setModel(userList);
                    users.setSelectedIndex(0);
                } else if (message.substring(0, 9).equals("[MESSAGE]")) {
                    textArea.append(message.substring(9) + "\n");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    public void ending() {
        end = false;
    }
}
