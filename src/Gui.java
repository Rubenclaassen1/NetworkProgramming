import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLOutput;
import java.util.*;



public class Gui extends Application {
    private Table table;
    private Canvas canvas;
    private boolean locked;
    private ArrayList<BufferedImage> cardImages;
    private BufferedImage[] foundationImages;

    private ObjectInputStream reader;
    private ObjectOutputStream writer;


    @Override
    public void start(Stage stage) throws Exception {
        cardImages = loadImage();
        foundationImages = loadFoundationImage();
        locked = false;
//        table = new Table();
//        for (Row row : table.getRows()) {
//            System.out.println("Row " + ": " + row);
//        }
        BorderPane mainPane = new BorderPane();
        canvas = new ResizableCanvas(g ->draw(g), mainPane);
        mainPane.setCenter(canvas);
        stage.setMinWidth(945);
        stage.setMinHeight(930);
        canvas.setOnMousePressed(event -> onMousePressed(event));
        canvas.setOnMouseReleased(event -> {if (previousStock != null && !selectedCards.isEmpty()) onMouseRelease(event);});
        canvas.setOnMouseDragged(event -> onMouseDrag(event));

        stage.setScene(new Scene(mainPane));
        Thread thread = new Thread(this::connection);
        thread.start();

        stage.show();

    }
    private void connection() {

        try {
            Socket socket = new Socket("26.159.100.51", 1337);
            writer = new ObjectOutputStream(socket.getOutputStream());
            reader = new ObjectInputStream(socket.getInputStream());
            Table serverTable = (Table) reader.readObject();
            System.out.println("connection");
            if (serverTable == null) {
                this.table = new Table();
                Arrays.stream(table.getRows()).forEach(System.out::println);
                writer.writeObject(table);
                writer.flush();
            } else {
                this.table = serverTable;
            }
            draw(new FXGraphics2D(canvas.getGraphicsContext2D()));
            while (socket.isConnected()) {
                table = (Table) reader.readObject();
                System.out.println("recieved a table");
                draw(new FXGraphics2D(canvas.getGraphicsContext2D()));

            }
            socket.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private void writeTable(){
        try {
            writer.reset();
            writer.writeObject(table);
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private Stack<Card> selectedCards = new Stack<Card>();
    private Stock previousStock;
    private double Xoffset;
    private double Yoffset;

    private void onMouseDrag(MouseEvent mouse) {
        if(selectedCards.isEmpty()) return;
        selectedCards.forEach(card -> card.setPosition(new Point2D.Double(mouse.getX() - Xoffset, (mouse.getY() - Yoffset) + (50 * selectedCards.indexOf(card)))));

        draw(new FXGraphics2D(canvas.getGraphicsContext2D()));
    }

    private void onMouseRelease(MouseEvent mouse) {
        Stock closestStock = getClosestStock(mouse);

        if ( closestStock == null || closestStock.getClass().equals(Pile.class)||!closestStock.addCard(selectedCards)) {
            previousStock.resetCard(selectedCards);
        } else if (previousStock.getClass().equals(Row.class)) {
            ((Row) previousStock).showLast();
        }

        selectedCards.clear();
        table.setHasSelectedCards(false);
        System.out.println("FALSE");
        writeTable();
        draw(new FXGraphics2D(canvas.getGraphicsContext2D()));
        if (gameDone()){
            endSequence();
        }
    }


    private void onMousePressed(MouseEvent mouse) {
        System.out.println("click");
        if(!locked && !table.getHasSelectedCards()){
            previousStock = getClosestStock(mouse);
            if (previousStock == table.getReserve()) {
                if (table.getReserve().getCards().isEmpty()) {
                    table.getReserve().resetCard(table.getPile().reset());
                } else {
                    table.getPile().addCard(table.getReserve().removeCard());
                }
                writeTable();
            }
            else{
                if (previousStock == null || previousStock.getCards().isEmpty()) {
                    return;
                }
                table.setHasSelectedCards(true);
                writeTable();

                if (previousStock.getClass().equals(Row.class)) {
                    selectedCards = ((Row) previousStock).removeCard(((Row) previousStock).getSelectedCard(mouse));
                } else if(previousStock != table.getReserve()) {
                    selectedCards.add(previousStock.getCards().pop());
                }

                if (!selectedCards.isEmpty()) {
                    Xoffset = mouse.getX() - selectedCards.firstElement().getPosition().getX();
                    Yoffset = mouse.getY() - selectedCards.firstElement().getPosition().getY();
                }
            }

        }

        draw(new FXGraphics2D(canvas.getGraphicsContext2D()));
    }



    private Stock getClosestStock(MouseEvent mouse) {
        return table.getAllStocks().stream().filter(stock -> stock.containsMouse(mouse)).findAny().orElse(null);
    }

    public boolean gameDone(){
        int counter = 0;
        for (Foundation foundation : table.getFoundations()) {
            if (foundation.isFull()){
                counter++;
            }
        }
        if (counter == table.getFoundations().length){
            locked = true;
            return true;

        }
        return false;
    }

    private void endSequence() {                //Start threads and a timer to draw cards
        Thread thread1 = new Thread(()-> animation(0));
        Thread thread2 = new Thread(()-> animation(1));
        Thread thread3 = new Thread(()-> animation(2));
        Thread thread4 = new Thread(()-> animation(3));


        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
        new AnimationTimer() {
            long last = -1;
            @Override
            public void handle(long now) {


                last = now;
                spacing++;
                if (spacing == 3) {
                    for (Foundation foundation : table.getFoundations()) {
                        if(!foundation.getCards().isEmpty()){
                            foundation.drawEnd(new FXGraphics2D(canvas.getGraphicsContext2D()),cardImages);
                        }
                    }
                    spacing = 0;
                }

            }
        }.start();

    }

    private int spacing = 0;

    public void update() {

    }

    private void animation(int i){                                 // End animation for foundation
        while (!table.getFoundations()[i].getCards().isEmpty()) {
            Card card = table.getFoundations()[i].getCards().get(table.getFoundations()[i].getCards().size()-1);
            if (card.getPosition().getY() < canvas.getHeight()) {
                card.update();
            } else {

                table.getFoundations()[i].getCards().pop();
            }
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }


    }
    private ArrayList<BufferedImage> loadImage() {
        ArrayList<BufferedImage> cards = new ArrayList<>();

        try {
            BufferedImage image = ImageIO.read(Objects.requireNonNull(getClass().getResource("playing_cards.png")));
            //knip de afbeelding op in 24 stukjes van 32x32 pixels.
            for (int i = 0; i < 52; i++) {
                cards.add(image.getSubimage(100 * (i % 13), 150 * (i / 13), 100, 150));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cards;
    }

    private BufferedImage[] loadFoundationImage() {
        BufferedImage[] foundations = new BufferedImage[4];

        try {
            BufferedImage foundationImage = ImageIO.read(Objects.requireNonNull(getClass().getResource("foundations.png")));
            for (int i = 0; i < 4; i++) {
                foundations[i] = foundationImage.getSubimage(100 * (i % 4), 0, 100, 150);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return foundations;
    }


    public void draw(FXGraphics2D graphics2D) {
        if(table == null){
            return;
        }
        graphics2D.setBackground(Color.decode("#006a12"));
        if (!locked){
            graphics2D.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());
        }
        for (Row row : table.getRows()) {
            for (Card card : row.getCards()) {
                card.draw(graphics2D,cardImages);
            }
        }
        for (Foundation foundation : table.getFoundations()) {
            foundation.draw(graphics2D,foundationImages,cardImages);
        }
        if (!table.getPile().getCards().isEmpty()) table.getPile().getCards().peek().draw(graphics2D,cardImages);
        if (!table.getReserve().getCards().isEmpty()) table.getReserve().getCards().peek().draw(graphics2D,cardImages);

        for (Card selectedCard : selectedCards) {
            selectedCard.draw(graphics2D,cardImages);
        }


    }




}