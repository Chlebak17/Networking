import connectionPractice.Canvas;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class EchoClient {
    public static String message = "";

    public static void main(String[] args) {
        String host = "127.0.0.1";
        int port = 4040;

        Canvas Canvas = new Canvas("Client Terminal");
        Canvas.setVisible(true);
        Canvas.textAppend("Připojuji se k " + host + ":" + port);

        try(Socket socket = new Socket(host, port)){
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            Canvas.textAppend("Připojeno k serveru");

            Canvas.textAppend("Zadej zpravu, pro ukonceni komunikace /quit");


            while(true){
                if(message.equalsIgnoreCase("/quit")){
                    break;
                }
                out.println(message);

                String response = in.readLine();
                if (response == null) {
                    Canvas.textAppend("Server ukončil spojení");
                }

                Canvas.textAppend(response);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
