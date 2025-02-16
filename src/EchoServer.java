import connectionPractice.Canvas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {
    public static void main(String[] args) {


        int port = 4040;
        Canvas Canvas = new Canvas("Terminal Server");
        Canvas.setVisible(true);


        Canvas.textAppend("Spoustím server na portu: " + port);

        try(ServerSocket serverSocket = new ServerSocket(port)){
            Canvas.textAppend("Server běží na portu: " + port);

            try(Socket socket = serverSocket.accept()){
                Canvas.textAppend("Client se připojil: " + socket.getInetAddress());

                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                String line = in.readLine();

                Canvas.okButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String userIn = Canvas.getInputField().toString();
                        Canvas.textAppend(userIn);
                    }
                });
                while (line != null) {
                    Canvas.textAppend(line);

                }
                Canvas.textAppend("Client se odpojil");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
