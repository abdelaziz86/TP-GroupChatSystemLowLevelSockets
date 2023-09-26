package Server;
import java.io.*;
import java.net.*;
public class ClientHandler implements Runnable {
    private Socket connectionSocket;

    public ClientHandler(Socket socket) {
        this.connectionSocket = socket;
    }

    @Override
    public void run() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(connectionSocket.getOutputStream()));

            writer.write("*** Welcome to the Calculation Server (Addition Only) ***\r\n");
            writer.write("*** Please type in the first number and press Enter : \n");
            writer.flush();

            String data1 = reader.readLine().trim();
            writer.write("*** Please type in the second number and press Enter :\n");
            writer.flush();

            String data2 = reader.readLine().trim();
            int num1 = Integer.parseInt(data1);
            int num2 = Integer.parseInt(data2);
            int result = num1 + num2;

            System.out.println("Addition operation done ");
            writer.write("\r\n=== Result is : \n" + result + "\n");
            writer.flush();

            // Close the connectionSocket
            connectionSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
