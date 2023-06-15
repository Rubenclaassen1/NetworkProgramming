import javafx.application.Application;
import javafx.scene.Group;
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
import java.util.Objects;
import java.util.Stack;


public class Gui extends Application {
    private static Table table;
    private Canvas canvas;


    @Override
    public void start(Stage stage) throws Exception {

        table = new Table(loadImage(), loadFoundationImage());
        for (Row row : table.getRows()) {
            System.out.println("Row " + ": " + row);
        }
        BorderPane mainPane = new BorderPane();
        canvas = new ResizableCanvas(g -> draw(g), mainPane);
        mainPane.setCenter(canvas);
        stage.setMinWidth(945);
        stage.setMinHeight(930);
        canvas.setOnMousePressed(event -> onMousePressed(event));
        canvas.setOnMouseReleased(event -> {if(previousStock != null && !selectedCards.isEmpty())onMouseRelease(event);});
        canvas.setOnMouseDragged(event -> onMouseDrag(event));
//        stage.setMaximized(true);
        stage.setScene(new Scene(mainPane));
        stage.setTitle("Solitaire");

        stage.show();

    }



    private Stack<Card> selectedCards = new Stack<Card>();
    private Stock previousStock;
    private double Xoffset;
    private double Yoffset;

    private void onMouseDrag(MouseEvent mouse) {
        if(selectedCards.isEmpty()) return;
        // System.out.println("Kanker");
        for (int i = 0; i < selectedCards.size(); i++) {
            selectedCards.get(i).setPosition(new Point2D.Double(mouse.getX() - Xoffset, (mouse.getY() - Yoffset) + (50 * i)));
        }
        draw(new FXGraphics2D(canvas.getGraphicsContext2D()));
    }

    private void onMouseRelease(MouseEvent mouse) {
        Stock closestStock = getClosestStock(mouse);

        System.out.println(closestStock);
        if ( closestStock == null || closestStock.getClass().equals(Pile.class)||!closestStock.addCard(selectedCards)) {
            System.out.println("return card to previous");
                previousStock.resetCard(selectedCards);
        } else if (previousStock.getClass().equals(Row.class)) {
                ((Row) previousStock).showLast();
        }
        selectedCards.clear();
        draw(new FXGraphics2D(canvas.getGraphicsContext2D()));
    }


    private void onMousePressed(MouseEvent mouse) {
        previousStock = getClosestStock(mouse);
        if(previousStock == null) {
            return;
        }

        if (getClosestStock(mouse) == table.getReserve()) {
            if(table.getReserve().getCards().isEmpty()){
                table.getReserve().resetCard(table.getPile().reset());
            }else {
                table.getPile().addCard(table.getReserve().removeCard());
            }
        }else {
            if (previousStock.getClass().equals(Row.class)) {
                Row closestRow = (Row) previousStock;
                selectedCards = closestRow.removeCard(closestRow.getSelectedCard(mouse));
                System.out.println(closestRow.getCards());
            }else{
                selectedCards.add(previousStock.getCards().pop());
            }
            if(!selectedCards.isEmpty()) {
                Xoffset = mouse.getX() - selectedCards.firstElement().getPosition().getX();
                Yoffset = mouse.getY() - selectedCards.firstElement().getPosition().getY();
            }
        }
        draw(new FXGraphics2D(canvas.getGraphicsContext2D()));

    }

    private Stock getClosestStock(MouseEvent mouse) {
        double closestDistance = 10000;
        Stock closestStock = null;
        double distance;
        for (Stock stock : table.getAllStocks()) {
            if(stock.getClass().equals(Row.class) &&  stock.getPosition().getY()> mouse.getY() || stock.getPosition().getY() + (stock.getCards().size()-1)*50 +150 < mouse.getY())continue;
            if (stock.getPosition().getX() + 100 < mouse.getX() || stock.getPosition().getX() > mouse.getX()) {continue;}

            distance = stock.getPosition().distance(mouse.getX(), mouse.getY());
            if (distance < closestDistance) {
                closestDistance = distance;
                closestStock = stock;
            }
        }
        return closestStock;
    }



    public void update() {


    }

    private BufferedImage[] loadImage() {
        BufferedImage[] cards = new BufferedImage[52];

        try {
            BufferedImage image = ImageIO.read(Objects.requireNonNull(getClass().getResource("playing_cards.png")));
            //knip de afbeelding op in 24 stukjes van 32x32 pixels.
            for (int i = 0; i < 52; i++) {
                cards[i] = image.getSubimage(100 * (i % 13), 150 * (i / 13), 100, 150);

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
        graphics2D.setBackground(Color.decode("#006a12"));
        graphics2D.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());
        for (Row row : table.getRows()) {
            for (Card card : row.getCards()) {
                card.draw(graphics2D);
            }
        }
        for (Foundation foundation : table.getFoundations()) {
            foundation.draw(graphics2D);
        }
        if (!table.getPile().getCards().isEmpty()) table.getPile().getCards().peek().draw(graphics2D);
        if (!table.getReserve().getCards().isEmpty()) table.getReserve().getCards().peek().draw(graphics2D);
        for (Card selectedCard : selectedCards) {
            selectedCard.draw(graphics2D);
        }


    }

    public static void main(String[] args) {


        launch(Gui.class);
    }

}
