import java.awt.geom.Point2D;
import java.util.Queue;
import java.util.Stack;

public class Foundation extends Stock{
    private Type type;


    public Foundation(Point2D position, Stack<Card> stockCards, Type type) {
        super(position, stockCards);
        this.type = type;

    }

    @Override
    public void addCard(Card card) {
        if(card.getType() == type && card.getValue() == super.getCards().size() +1){
            card.setPosition(super.getPostition());
            super.getCards().add(card);
        }else{
            System.out.println("Is niet de juiste kaart");
        }
    }

    @Override
    public String toString() {
        return "Foundation{" +
                "type=" + type +
                '}';
    }
}
