package Client;

import javax.swing.*;

public class chat {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Create and display multiple chat client GUIs
                new mychat("Client 1");
                new mychat("Client 2");
                new mychat("Client 3");
                // Add more clients as needed
            }
        });
    }
}
