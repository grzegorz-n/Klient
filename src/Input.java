import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Input extends Thread {
    private Socket socket;
    private BufferedReader input;
    private JTextArea textArea;
    private boolean end;

    public Input(Socket socket, JTextArea textArea) {
        this.socket = socket;
        this.textArea = textArea;
        this.end = true;
    }
    public void run() {
        try {
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while (end) {
                String message = input.readLine();
                if (message.equals("EXIT")) {
                    Thread.sleep(100);
                    break;
                }
                textArea.append(message + "\n");
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
