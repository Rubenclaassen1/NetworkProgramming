
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import org.jfree.fx.FXGraphics2D;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.ArrayList;

public class Card implements Serializable {
    public final int CARD_WIDTH = 100;
    public final int CARD_HEIGHT = 150;
    private final int value;
    private final Type type;
    private final CardColor cardColor;
    private boolean showing;
    private Point2D position;
    private int imageIndex;
    private Point2D speed;



    public Card(int value, Type type, CardColor cardColor, boolean showing, Point2D position, int imageIndex) {
        this.value = value;
        this.type = type;
        this.cardColor = cardColor;
        this.showing = showing;
        this.position = position;
        this.imageIndex = imageIndex;
        this.speed = new Point2D.Double(Math.random()*10-5, 5);
    }

    public Point2D getPosition() {
        return position;
    }

    public void setPosition(Point2D position) {
        this.position = position;
    }

    public boolean isShowing() {
        return showing;
    }
    public void setShowing(boolean showing){
        this.showing = showing;
    }

    public int getValue() {
        return value;
    }

    public Type getType() {
        return type;
    }

    public CardColor getCardColor() {
        return cardColor;
    }

    @Override
    public String toString() {
        return "Card{" +
                "value=" + value +
                ", type=" + type +
                ", cardColor=" + cardColor +
                ", showing=" + showing +
                '}';
    }
    public void draw(FXGraphics2D graphics2D, ArrayList<BufferedImage> cardImages){
        AffineTransform fx = new AffineTransform();
        Shape card = new Rectangle2D.Double(position.getX(), position.getY(), 100, 150);
        if(!showing){
            graphics2D.setColor(Color.BLUE);
            graphics2D.fill(card);
        } else {
            graphics2D.setColor(Color.white);
            graphics2D.fill(card);
            fx.translate(position.getX(), position.getY());
           graphics2D.drawImage(cardImages.get(imageIndex),fx,null);
        }
        graphics2D.setColor(Color.BLACK);
        graphics2D.draw(card);
    }

    public void update(){

        this.position = new Point2D.Double(this.position.getX() + this.speed.getX(), this.position.getY() + this.speed.getY());
    }

}
enum Type{

    CLUBS,
    SPADES,
    DIAMONDS,
    HEARTS
}
enum CardColor {
    BLACK,
    RED
}

