import javafx.scene.input.MouseEvent;

import java.awt.geom.Point2D;
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


    public boolean addCard(Stack<Card> stackCards) {

        Card firstCard = stackCards.firstElement();
        Stack<Card> superStack = super.getCards();
        if (superStack.isEmpty() && firstCard.getValue() != 13) {
            return false;
        } else if (!superStack.isEmpty()) {
            if (firstCard.getCardColor() == superStack.peek().getCardColor() || firstCard.getValue() != superStack.peek().getValue() - 1) {
                return false;
            }
        }
        Double lastYPos = (super.getCards().size()-1)*50 + super.getPosition().getY();
        for (Card card : stackCards) {
            lastYPos+=50;
            card.setPosition(new Point2D.Double(super.getPosition().getX(), lastYPos));
        }
        super.getCards().addAll(stackCards);
        return true;
    }

    @Override
    public void resetCard(Stack<Card> cards) {
        double lastYPos = (super.getCards().size()-1)*50 +super.getPosition().getY();
        for (Card card :cards) {
            lastYPos+=50;
            card.setPosition(new Point2D.Double(super.getPosition().getX(), lastYPos));
        }
        super.getCards().addAll(cards);
    }

    public Stack<Card> removeCard(Card card){
        if(card.isShowing()){
            Stack<Card> cardStack = new Stack<>();
            cardStack.addAll(super.getCards().subList( super.getCards().size()-super.getCards().search(card),super.getCards().size()));
            System.out.println(super.getCards().search(card));
            System.out.println(cardStack);
            super.getCards().subList( super.getCards().size()-super.getCards().search(card),super.getCards().size()).clear();

            return cardStack;
        }
        System.out.println("no cards shown");
        return null;

    }
    public void showLast(){
        if(!super.getCards().isEmpty())super.getCards().peek().setShowing(true);
    }


    public Card getSelectedCard(MouseEvent mouse) {
        Card selectedCard;
        if (mouse.getX() - super.getPosition().getX() < 150 && mouse.getX() - super.getPosition().getX() > 0) {

            int selectedIndex = (int) ((mouse.getY() - super.getPosition().getY()) / 50);
            if (selectedIndex >= super.getCards().size()){
                selectedIndex = super.getCards().size()-1;
            }
            selectedCard = super.getCards().get(selectedIndex);

            return selectedCard;
        }
        return null;
    }
}

