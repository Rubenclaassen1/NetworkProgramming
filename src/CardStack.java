import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Queue;

public interface CardStack {
    void SetPositition(Point2D position);
    void AddCard(Card card);
    Point2D GetPostition();
    Queue<Card> getCards();



}
