import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Queue;
import java.util.Stack;

public class Row extends Stock {


    public Row(Point2D position, Stack<Card> stockCards) {
        super(position, stockCards);
        super.getCards().peek().setShowing(true);
        for (int i = 0; i < stockCards.size(); i++) {
            super.getCards().get(i).setPosition(new Point2D.Double(position.getX(),position.getY()+50*i));
        }
    }


//    public void generateRow(Card card){
//        super.getCards().add(card);
//    }

    @Override
    public void addCard(Card card) {
        Stack<Card> superStack = super.getCards();
        if (superStack.isEmpty() && card.getValue() != 13) {
            return;
        } else if (!superStack.isEmpty()) {
            if (card.getCardColor() == superStack.peek().getCardColor() || card.getValue() != superStack.peek().getValue() - 1) {
                return;
            }
        }
        super.getCards().add(card);
    }
    public void addCard(Stack<Card> stackCards) {
        Card lastCard = stackCards.firstElement();
        Stack<Card> superStack = super.getCards();
        if (superStack.isEmpty() && lastCard.getValue() != 13) {
            return;
        } else if (!superStack.isEmpty()) {
            if (lastCard.getCardColor() == superStack.peek().getCardColor() || lastCard.getValue() != superStack.peek().getValue() - 1) {
                return;
            }
        }
        super.getCards().addAll(stackCards);
    }
    public Stack<Card> removeCard(Card card){
        if(card.isShowing()){
            Stack<Card> cardStack = new Stack<>();
            cardStack.addAll(super.getCards().subList(super.getCards().search(card),super.getCards().size()));
            super.getCards().subList(super.getCards().search(card),super.getCards().size()).clear();
            return cardStack;
        }
        System.out.println("no cards shown");
        return null;


    }


}

