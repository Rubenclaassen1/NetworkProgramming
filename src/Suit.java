import java.awt.geom.Point2D;
import java.util.Queue;

public class Suit extends Stock{
    public Suit(Point2D position, Queue<Card> stockCards) {
        super(position, stockCards);
    }

    @Override
    public void AddCard(Card card) {

    }
}
