package connectionPractice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.spec.RSAOtherPrimeInfo;
import java.util.Scanner;

public class JClient {
    public static void main(String[] args) {
        int port = 8080;
        String host = "127.0.0.1";

        try(Socket clientSocket = new Socket(host,port)){
            System.out.println("Připojen na server: " + clientSocket.getInetAddress());

            //příjmač
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            //odesílač
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            
            Thread serverReader = new Thread(() -> {
                
            });
            
            Scanner sc = new Scanner(System.in);



            System.out.println("Pro ukončení zprávy napište: 'konec'" );
            while (true) {
                String message = sc.nextLine();
                if (message.equalsIgnoreCase("konec")) {
                    break;
                }
                out.println(message);

                String response = in.readLine();
                if (response == null) {
                    break;
                }
                System.out.println("Od serveru přišlo:" + response);
            }
            in.close();
            out.close();
            System.out.println("Server se ukončil...");
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
