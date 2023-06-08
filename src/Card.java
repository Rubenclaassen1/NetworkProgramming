import java.awt.geom.Point2D;
import java.util.Enumeration;

public class Card {
    private final int value;
    private final Type type;
    private final Color cardColor;
    private boolean showing;
    private Point2D position;

    public Card(int value, Type type, Color cardColor, boolean showing, Point2D position) {
        this.value = value;
        this.type = type;
        this.cardColor = cardColor;
        this.showing = showing;
        this.position = position;
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

    public Color getCardColor() {
        return cardColor;
    }
}
enum Type{
    HEARTS,
    CLUBS,
    SPADES,
    DIAMONDS;
}
enum Color{
    BLACK,
    RED;
}

