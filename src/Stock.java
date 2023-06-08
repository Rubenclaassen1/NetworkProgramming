import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Queue;


public class Stock implements CardStack{
    private Point2D position;
    private Queue<Card> stockCards;

    public Stock(Point2D position, Queue<Card> stockCards) {
        this.position = position;
        this.stockCards = stockCards;
    }

    @Override
    public void SetPositition(Point2D position) {
        this.position = position;
    }

    @Override
    public void AddCard(Card card) {

    }

    @Override
    public Point2D GetPostition() {
        return this.position;
    }

    @Override
    public Queue getCards() {
        return this.stockCards;
    }
}
