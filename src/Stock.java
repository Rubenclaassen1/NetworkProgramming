import java.awt.geom.Point2D;
import java.util.Stack;


public abstract class Stock{
    private Point2D position;
    private Stack<Card> stockCards;

    public Stock(Point2D position, Stack<Card> stockCards) {
        this.position = position;
        this.stockCards = stockCards;
    }

    public abstract boolean addCard(Stack<Card> cards);
    public abstract void resetCard(Stack<Card> cards);




    public void setPosition(Point2D position) {
        this.position = position;
    }

    public Card removeCard() {
        return stockCards.pop();

    }

    public Point2D getPosition() {
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
