package Server;

import java.io.*;
import java.net.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ClientHandler implements Runnable {
    private static final Map<Socket, BufferedWriter> clientWriters = new ConcurrentHashMap<>();
    private Socket clientSocket;
    private BufferedReader reader;

    public ClientHandler(Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;
        reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        clientWriters.put(clientSocket, new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream())));
    }

    @Override
    public void run() {
        try {
            String message;
            while ((message = reader.readLine()) != null) {
                if (message.startsWith("/broadcast ")) {
                    broadcastMessage(message.substring("/broadcast ".length()));
                } else {
                    System.out.println("Received message: " + message);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void broadcastMessage(String message) {
        for (Socket client : clientWriters.keySet()) {
            try {
                BufferedWriter writer = clientWriters.get(client);
                writer.write(message + "\r\n");
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
