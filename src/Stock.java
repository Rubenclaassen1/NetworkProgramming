import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Queue;
import java.util.Stack;


public abstract class Stock implements CardStack{
    private Point2D position;
    private Stack<Card> stockCards;

    public Stock(Point2D position, Stack<Card> stockCards) {
        this.position = position;
        this.stockCards = stockCards;
    }

    public abstract void addCard(Card card);

    public void setPositition(Point2D position) {
        this.position = position;
    }

    public Card removeCard() {
        return stockCards.pop();
    }

    public Point2D getPostition() {
        return this.position;
    }

    public Stack<Card> getCards() {
        return this.stockCards;
    }

    @Override
    public String toString() {
        return "Stock{" +
                "stockCards=" + stockCards +
                '}';
    }
}
