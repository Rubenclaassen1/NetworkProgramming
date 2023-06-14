import java.awt.geom.Point2D;
import java.util.Stack;

public class Pile extends Stock{
    public Pile(Point2D position, Stack<Card> stockCards) {
        super(position, stockCards);
    }

    @Override
    public boolean addCard(Stack<Card> cards) {
        if (cards.size()==1){

            cards.get(0).setShowing(true);
            super.getCards().add(cards.get(0));
            return true;
        }
        return false;
    }
    public Stack<Card> reset(){
        for (Card card : super.getCards()) {
            card.setShowing(false);
        }
        Stack<Card> cardStack = new Stack<>();
        cardStack.addAll(super.getCards());
        super.getCards().clear();
        return cardStack;
    }
}
