package connectionPractice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class JServer {
    public static void main(String[] args) {
        int port = 8080;

        try(ServerSocket serverSocket = new ServerSocket(port)){
            System.out.println("Server se spustil na portu " + port);

            try(Socket clientSocket = serverSocket.accept()){
                System.out.println("Client connected: " + clientSocket.getInetAddress());

                //Zprávy od klienta
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                //Zprávy klientovy
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
//                out.println("Byl jste připojen na server");
                System.out.println("Čekám na odpověď...");
                while(true){
                    String response = in.readLine();
                    if (response == null) {
                        break;
                    }
                    System.out.println("Od klienta přišlo: " + response);

                    out.println("Získal jsem zprávu!");


                }

            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
