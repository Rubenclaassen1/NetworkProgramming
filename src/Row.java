import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Queue;

public class Row extends Stock {
    private ArrayList<Card> stockCards;

    public Row(Point2D position, Queue<Card> stockCards) {
        super(position, stockCards);
        this.stockCards = new ArrayList<Card>(stockCards);
    }

    @Override
    public void AddCard(Card card) {

    }
}
