import org.jfree.fx.FXGraphics2D;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.Queue;
import java.util.Stack;

public class Foundation extends Stock{
    private Type type;
    private BufferedImage foundation;



    public Foundation(Point2D position, Stack<Card> stockCards, Type type, BufferedImage foundation) {
        super(position, stockCards);
        this.type = type;
        this.foundation = foundation;

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

    public void draw(FXGraphics2D graphics2D) {
        int stacksize = super.getCards().size();
        if(super.getCards().isEmpty()){
            AffineTransform fx = new AffineTransform();
            fx.translate(super.getPostition().getX(), super.getPostition().getY());
            graphics2D.drawImage(foundation, fx, null);
        } else if (stacksize >=2){
            super.getCards().get(stacksize-1).draw(graphics2D);
            super.getCards().get(stacksize).draw(graphics2D);

        } else{
            super.getCards().get(stacksize).draw(graphics2D);
        }

    }
}
