import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.Collections;
import java.util.Stack;

public class Table {
    private Stack<Card> deck;
    private Row[] rows = new Row[7];
    private Pile stock;
    private Pile pile;
    private Foundation[] foundations = new Foundation[4];

    public Table(BufferedImage[] cards) {
        generateCards(cards);
        shuffle();
        fillStocks();
    }


    private void shuffle() {
        Collections.shuffle(deck);
    }

    private void generateCards(BufferedImage[] cards) {
        int counter = 0;
        deck = new Stack<>();
        for (int i = 0; i < 4; i++) {
            for (int j = 1; j < 14; j++) {
                deck.add(new Card(j, Type.values()[i], CardColor.values()[(i / 2)], false, null,cards[counter]));
                counter++;
            }
        }
        for (Card card : deck) {
            System.out.println(card);
        }
    }
    private void fillStocks(){
        Stack<Card> dealingDeck = new Stack<>();
        dealingDeck.addAll(deck);
        for (int i = 0; i < foundations.length; i++) {
            foundations[i] = new Foundation(null, new Stack<>(), Type.values()[i]);
        }
        for (int i = 0; i < rows.length; i++) {
            Stack<Card> cardStack = new Stack<>();
            cardStack.addAll(dealingDeck.subList(0,i+1));
            dealingDeck.subList(0,i+1).clear();
            rows[i] = new Row(new Point2D.Float(110*i + 100,200 ),cardStack);
        }
        stock = new Pile(null,dealingDeck);
        System.out.println(deck.size());
    }



    public Stack<Card> getDeck() {
        return deck;
    }

    public Row[] getRows() {
        return rows;
    }

    public Stock getStock() {
        return stock;
    }

    public Pile getPile() {
        return pile;
    }

    public Foundation[] getFoundations() {
        return foundations;
    }
}
