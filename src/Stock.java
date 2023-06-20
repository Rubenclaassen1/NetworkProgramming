import javafx.scene.input.MouseEvent;

import java.awt.geom.Point2D;
import java.io.Serializable;
import java.util.Stack;


public abstract class Stock implements Serializable {
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
    public boolean containsMouse(MouseEvent mouse){
        if(getClass().equals(Row.class)){
            return position.getX() < mouse.getX()
                    && position.getX() + 100 > mouse.getX()
                    && position.getY() < mouse.getY()
                    && position.getY() + (stockCards.size()-1)*50+150 > mouse.getY();
        }
        return position.getX() < mouse.getX()
                && position.getX() + 100 > mouse.getX()
                && position.getY() < mouse.getY()
                && position.getY() + 150 > mouse.getY();
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
