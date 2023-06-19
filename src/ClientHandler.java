import java.io.*;

import java.net.Socket;

public class ClientHandler {
    private Socket socket;
    private ObjectInputStream reader;
    private ObjectOutputStream writer;

    public ClientHandler(Socket socket) {
        this.socket = socket;
        try {
            writer = new ObjectOutputStream(socket.getOutputStream());
            reader = new ObjectInputStream(socket.getInputStream());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("clienthandler");
        Thread thread = new Thread(this::recieve);
        thread.start();

    }

    private void recieve() {
        try {

            while (socket.isConnected()) {
                Table table = (Table) reader.readObject();
                if (table != null) {
                    Server.send(table);
                }

            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        Server.removeClient(this);




    }


    public void write(Table table){
        try {
            writer.writeObject(table);
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
