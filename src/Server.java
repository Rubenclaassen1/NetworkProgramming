import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class Server {

    private static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
    private static Table serverTable;
    public static void main(String[] args) throws IOException {

        Socket socket;
        ServerSocket serverSocket = new ServerSocket(1337);
        while(true) {
            socket = serverSocket.accept();
            System.out.println("a client has connected");
            System.out.println("new client id:" + clientHandlers.size());
            ClientHandler client = new ClientHandler(socket,clientHandlers.size());
            client.write(serverTable);
            clientHandlers.add(client);
        }
    }


    public static void send(Table table){
        serverTable = table;
        Arrays.stream(table.getRows()).forEach(System.out::println);
        for (ClientHandler clientHandler : clientHandlers) {
             clientHandler.write(serverTable);

        }
    }
    public static void sendAllOthers(Table table, ClientHandler client){
       serverTable = table;
        for (ClientHandler clientHandler : clientHandlers) {
            if(clientHandler.equals(client)) {
                Arrays.stream(table.getRows()).forEach(System.out::println);
                continue;
            }
            clientHandler.write(table);

        }

    }
    public static void removeClient(ClientHandler client){
        clientHandlers.remove(client);

        if (clientHandlers.isEmpty()){
            serverTable = null;
        }
    }
}
