import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Objects;


public class Gui extends Application {
    private static Table table;
    private Canvas canvas;

    @Override
    public void start(Stage stage) throws Exception {
        table = new Table(loadImage());
        for (Row row : table.getRows()) {
            System.out.println("Row " +": "+ row);
        }
        canvas = new Canvas(1920, 1200);

        stage.setMaximized(true);
        stage.setScene(new Scene(new Group(canvas)));
        stage.setTitle("Solitaire");
        draw(new FXGraphics2D(canvas.getGraphicsContext2D()));
        stage.show();

    }
    public void update(){


    }

    private BufferedImage[] loadImage(){
        BufferedImage[] cards = new BufferedImage[52];
        try {
            BufferedImage image = ImageIO.read(Objects.requireNonNull(getClass().getResource("playing_cards.png")));
            //knip de afbeelding op in 24 stukjes van 32x32 pixels.
            for(int i = 0; i < 52; i++) {
                cards[i] = image.getSubimage(100 * (i % 13), 150 * (i / 13), 100, 150);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cards;
    }


    public void draw(FXGraphics2D graphics2D){
        graphics2D.setBackground(Color.decode("#006a12"));
        graphics2D.clearRect(0,0,(int)canvas.getWidth(),(int)canvas.getHeight());
        for (Row row : table.getRows()) {
            for (Card card : row.getCards()) {
                card.draw(graphics2D);
            }
        }
    }

    public static void main(String[] args) {


        launch(Gui.class);
    }

}
