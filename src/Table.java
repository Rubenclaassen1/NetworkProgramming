import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

public class Table {
    private Stack<Card> deck;
    private Row[] rows = new Row[7];
    private Pile stock;
    private Pile pile;
    private Foundation[] foundations = new Foundation[4];

    public Table() {
        generateCards();
        shuffle();
        fillStocks();
    }


    private void shuffle() {
        Collections.shuffle(deck);
    }

    private void generateCards() {
        deck = new Stack<>();
        for (int i = 0; i < 4; i++) {
            for (int j = 1; j < 14; j++) {
                deck.add(new Card(j, Type.values()[i], Color.values()[(i / 2)], false, null));
            }
        }
    }
    private void fillStocks(){
        for (int i = 0; i < rows.length; i++) {
            Stack<Card> cardStack = new Stack<>();
            cardStack.addAll(deck.subList(0,i+1));
            deck.subList(0,i+1).clear();
            rows[i] = new Row(null,cardStack);
        }
        stock = new Pile(null,deck);
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
