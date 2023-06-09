import org.jfree.fx.FXGraphics2D;

import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Stack;

public class Foundation extends Stock {
    private Type type;
    private int imageIndex;


    public Foundation(Point2D position, Stack<Card> stockCards, Type type, int imageIndex ) {
        super(position, stockCards);
        this.type = type;
        this.imageIndex = imageIndex;
        for (Card stockCard : stockCards) {
            stockCard.setPosition(position);
            stockCard.setShowing(true);
        }

    }

    @Override
    public boolean addCard(Stack<Card> cards) {
        if (cards.size() == 1) {
            if (cards.get(0).getType() == type && cards.get(0).getValue() == super.getCards().size() + 1) {
                cards.get(0).setPosition(super.getPosition());
                super.getCards().add(cards.get(0));
                return true;
            }
        }
        return false;

    }

    @Override
    public void resetCard(Stack<Card> cards) {
        for (Card card :cards) {
            card.setPosition(super.getPosition());
        }
        super.getCards().addAll(cards);
    }

    @Override
    public String toString() {
        return "Foundation{" +
                "type=" + type +
                '}';
    }

    public void draw(FXGraphics2D graphics2D, BufferedImage[] foundationImages, ArrayList<BufferedImage> cardImages) {
        int stacksize = super.getCards().size();
        if (super.getCards().isEmpty()) {
            AffineTransform fx = new AffineTransform();
            fx.translate(super.getPosition().getX(), super.getPosition().getY());
            graphics2D.drawImage(foundationImages[imageIndex], fx, null);
        } else if (stacksize >= 2) {
            super.getCards().get(stacksize - 2).draw(graphics2D,cardImages);
            super.getCards().get(stacksize-1).draw(graphics2D,cardImages);

        } else {
            super.getCards().get(stacksize-1).draw(graphics2D,cardImages);
        }

    }

    public void drawEnd(FXGraphics2D graphics2D,ArrayList<BufferedImage> cardImages){
        super.getCards().get(super.getCards().size()-1).draw(graphics2D,cardImages);
    }


    public boolean isFull(){
        return super.getCards().size() == 13;
    }
}
