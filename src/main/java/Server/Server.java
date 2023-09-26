package Server;
import java.io.*;
import java.net.*;

public class Server {
    public static void main(String[] args) throws IOException {
        System.out.println("Server is Running");
        ServerSocket mysocket = new ServerSocket(5555);

        while (true) {
            // Accept a client connection
            Socket connectionSocket = mysocket.accept();
            System.out.println("Accepted connection from client.");

            // Create a new thread to handle the client
            ClientHandler clientHandler = new ClientHandler(connectionSocket);

            // Start the thread
            Thread clientThread = new Thread(clientHandler);
            clientThread.start();
        }
    }
}
