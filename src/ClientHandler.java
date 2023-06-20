import java.io.*;

import java.net.Socket;
import java.util.Arrays;

public class ClientHandler {
    private Socket socket;
    private ObjectInputStream reader;
    private ObjectOutputStream writer;
    private int id;

    public ClientHandler(Socket socket, int id) {
        this.id =id;
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
    public int getId(){
        return this.id;
    }

    private void recieve() {
        try {

            while (socket.isConnected()) {
                Table table = (Table) reader.readObject();
                Arrays.stream(table.getRows()).forEach(System.out::println);
                if (table != null) {
//                    Server.send(table);
                    Server.sendAllOthers(table, this);

                }
            }
        } catch (Exception e) {
            try {
                writer.close();
                reader.close();
                Server.removeClient(this);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
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
