import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Queue;


public abstract class Stock implements CardStack{
    private Point2D position;
    private Queue<Card> stockCards;

    public Stock(Point2D position, Queue<Card> stockCards) {
        this.position = position;
        this.stockCards = stockCards;
    }

    public abstract void AddCard(Card card);

    public void SetPositition(Point2D position) {
        this.position = position;
    }

    public void removeCard() {

    }

    public Point2D GetPostition() {
        return this.position;
    }

    public Queue getCards() {
        return this.stockCards;
    }
}
