package Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class mychat {
    public JTextField tx;
    public JTextArea ta;
    public String login = "Imed";
    private BufferedWriter writer;
    private Socket socket;

    public mychat(String l) {
        login = l;

        JFrame f = new JFrame(login);
        f.setSize(600, 600);

        JPanel p1 = new JPanel();
        p1.setLayout(new BorderLayout());

        JPanel p2 = new JPanel();
        p2.setLayout(new BorderLayout());

        tx = new JTextField();
        p1.add(tx, BorderLayout.CENTER);

        JButton b1 = new JButton("Send");
        p1.add(b1, BorderLayout.EAST);

        ta = new JTextArea();
        ta.setEditable(false); // Make the text area read-only
        p2.add(ta, BorderLayout.CENTER);
        p2.add(p1, BorderLayout.SOUTH);

        f.setContentPane(p2);

        try {
            socket = new Socket("localhost", 5555);
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        b1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                String message = tx.getText();
                if (!message.isEmpty()) {
                    sendMessage(message);
                }
            }
        });

        // Listen for incoming messages from the server in a separate thread
        Thread listenThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String message;
                    while ((message = reader.readLine()) != null) {
                        ta.append(message + "\n");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        listenThread.start();

        f.setVisible(true);
    }

    private void sendMessage(String message) {
        try {
            String formattedMessage = "/broadcast " + login +" : " + message; // Prepend "/broadcast" to indicate broadcasting
            writer.write(formattedMessage);
            writer.write("\r\n");
            writer.flush();
            tx.setText(""); // Clear the input field after sending the message
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
