import javafx.scene.input.MouseEvent;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Queue;
import java.util.Stack;

public class Row extends Stock {

    private Double lastYPos;

    public Row(Point2D position, Stack<Card> stockCards) {
        super(position, stockCards);
        super.getCards().peek().setShowing(true);
        lastYPos = position.getY()+50*stockCards.size();
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
        lastYPos += 50;
        card.setPosition(new Point2D.Double(super.getPostition().getX(), lastYPos));
        super.getCards().add(card);
    }
    public void addCard(Stack<Card> stackCards) {
        Card firstCard = stackCards.firstElement();
        Stack<Card> superStack = super.getCards();
        if (superStack.isEmpty() && firstCard.getValue() != 13) {
            return;
        } else if (!superStack.isEmpty()) {
            if (firstCard.getCardColor() == superStack.peek().getCardColor() || firstCard.getValue() != superStack.peek().getValue() - 1) {
                return;
            }
        }
        for (Card card : stackCards) {
            lastYPos+=50;
            card.setPosition(new Point2D.Double(super.getPostition().getX(), lastYPos));
        }
        super.getCards().addAll(stackCards);
    }
    public Stack<Card> removeCard(Card card){
        if(card == null) return null;

        if(card.isShowing()){
            Stack<Card> cardStack = new Stack<>();
            cardStack.addAll(super.getCards().subList( super.getCards().size()-super.getCards().search(card),super.getCards().size()));
            System.out.println(super.getCards().search(card));
            System.out.println(cardStack);
            super.getCards().subList( super.getCards().size()-super.getCards().search(card),super.getCards().size()).clear();
            if(!super.getCards().isEmpty())super.getCards().peek().setShowing(true);
            return cardStack;
        }
        System.out.println("no cards shown");
        return null;

    }


    public Card getSelectedCard(MouseEvent mouse) {
        Card selectedCard;
        if (mouse.getX() - super.getPostition().getX() < 150 && mouse.getX() - super.getPostition().getX() > 0) {

            int selectedIndex = (int) ((mouse.getY() - super.getPostition().getY()) / 50);
            if (selectedIndex >= super.getCards().size()){
                selectedIndex = super.getCards().size()-1;
            }
            selectedCard = super.getCards().get(selectedIndex);

            return selectedCard;
        }
        return null;
    }
}

