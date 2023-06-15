import java.awt.geom.Point2D;
import java.util.Stack;

public class Pile extends Stock{
    public Pile(Point2D position, Stack<Card> stockCards) {

        super(position, stockCards);
        for (Card stockCard : stockCards) {
            stockCard.setPosition(position);
        }
    }

    @Override
    public boolean addCard(Stack<Card> cards) {
        if(cards.isEmpty()) return false;
        for (Card card : cards) {
            card.setShowing(false);
        }
        super.getCards().addAll(cards);
        return true;
    }


    public void addCard(Card card) {
        card.setShowing(true);
        card.setPosition(super.getPosition());
        super.getCards().add(card);
    }

    @Override
    public void resetCard(Stack<Card> cards) {
        for (Card card : cards) {
            card.setPosition(super.getPosition());
        }

        super.getCards().addAll(cards);
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
