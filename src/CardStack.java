import java.awt.geom.Point2D;
import java.util.Queue;
import java.util.Stack;

public interface CardStack {
    void setPositition(Point2D position);
    void addCard(Card card);
    Point2D getPostition();
    Stack<Card> getCards();



}
