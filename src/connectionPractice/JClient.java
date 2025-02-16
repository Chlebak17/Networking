package connectionPractice;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.spec.RSAOtherPrimeInfo;
import java.util.Scanner;
import java.util.function.ToDoubleBiFunction;

public class JClient {
    public static boolean isConnected = false;
    public static String endCom = "Klient se ukončil...";

    public static void endComunnication(PrintWriter out, Socket clientSocket, Thread serverReader, Canvas canvas) throws IOException, InterruptedException {
        out.close();
        clientSocket.close();
        serverReader.join();

        System.out.println(endCom);
        canvas.textAppend(endCom);

        System.exit(-1);
    }

    public static void main(String[] args) {
        int port = 8080;
        String host = "127.0.0.1";

        try(Socket clientSocket = new Socket(host,port)){
            String startCom = "Pro ukončení zprávy napište: 'konec'";
            Canvas canvas = new Canvas("Client " + clientSocket.getInetAddress().toString().replace("/","")+ ":" + clientSocket.getPort());

            canvas.textAppend("Připojen na server: " + clientSocket.getInetAddress());
            System.out.println("Připojen na server: " + clientSocket.getInetAddress());

            isConnected = true;

            Thread serverReader = new Thread(() -> {
                //příjmač
                BufferedReader in = null;
                try {
                    in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    while (isConnected){
                        String response = in.readLine();
                        canvas.textAppend("Od serveru přišlo:" + response);
                        System.out.println("Od serveru přišlo:" + response);
                    }
                } catch (IOException e) {
                    canvas.textAppend("Server neodesílá zprávy");
                    System.out.println("Server neodesílá zprávy");
                }
            });
            canvas.setVisible(true);
            serverReader.start();

            //odesílač
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            canvas.okButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                        if(isConnected){
                            String message = canvas.getInputField();

                            if (message.equalsIgnoreCase("konec")) {
                                isConnected = false;
                                canvas.setVisible(false);
                                try {
                                    endComunnication(out, clientSocket,serverReader,canvas);
                                } catch (IOException ex) {
                                    throw new RuntimeException(ex);
                                } catch (InterruptedException ex) {
                                    throw new RuntimeException(ex);
                                }

                            }
                            out.println(message);
                            canvas.setInputField("");
                        }

                    }
            });


            System.out.println(startCom);
            canvas.textAppend(startCom);
//            Scanner sc = new Scanner(System.in);

            //Zadávání příkazu skrze terminal
            /*
            while (isConnected) {


                String message = sc.nextLine();
                if (message.equalsIgnoreCase("konec")) {
                    isConnected = false;
                    break;
                }
                out.println(message);

            }
                */
            while (isConnected){

            }

        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}