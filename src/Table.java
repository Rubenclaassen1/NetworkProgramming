import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;


public class Table implements Serializable {
    private Stack<Card> deck;
    private Row[] rows = new Row[7]; //rows of cards
    private Pile reserve;              // pile of cards in reserve
    private Pile pile;               // pile of shown cards
    private Stack<Card> selectedCards = new Stack<>();
    private Foundation[] foundations = new Foundation[4];  //piles where you finish
    private ArrayList<Stock> allStocks = new ArrayList<>();

    public Table() {
        generateCards();
        shuffle();
        fillStocks();
//       createEndingDeck(cards, foundations);

    }

    public Stack<Card> getSelectedCards() {
        return selectedCards;
    }

    public void setSelectedCards(Stack<Card> selectedCards) {
        this.selectedCards = selectedCards;
    }

    private void reverse(){
        Collections.reverse(deck);
    }
    private void shuffle() {
        Collections.shuffle(deck);
    }

    private void generateCards() {
        deck = new Stack<>();
        for (int i = 0; i < 52; i++) {
            deck.add(new Card(i%13 +1, Type.values()[i/13], CardColor.values()[(i/13)/ 2], false, null, i));
        }
        deck.forEach(System.out::println);
    }
    private void fillStocks(){
        Stack<Card> dealingDeck = new Stack<>();
        dealingDeck.addAll(deck);
        for (int i = 0; i < foundations.length; i++) {
            foundations[i] = new Foundation(new Point2D.Double(355+i*150,25), new Stack<>(), Type.values()[i], i);
        }
        for (int i = 0; i < rows.length; i++) {
            Stack<Card> cardStack = new Stack<Card>();
            cardStack.addAll(dealingDeck.subList(0,i+1));
            dealingDeck.subList(0,i+1).clear();
            rows[i] = new Row(new Point2D.Float(130*i +25,200 ),cardStack);
        }
        reserve = new Pile(new Point2D.Double(25,25),dealingDeck);
        pile = new Pile(new Point2D.Double(155,25),new Stack<Card>());
        System.out.println(deck.size());
        allStocks.add(reserve);
        allStocks.add(pile);
        allStocks.addAll(Arrays.asList(rows));
        allStocks.addAll(Arrays.asList(foundations));
    }

    private void createEndingDeck(){
        generateCards();
        Stack<Card> dealingDeck = new Stack<>();
        dealingDeck.addAll(deck);
        for (int i = 0; i < 3; i++) {
            Stack<Card> cardStack = new Stack<>();
            cardStack.addAll(dealingDeck.subList(0,13));
            dealingDeck.subList(0,13).clear();
            foundations[i] = new Foundation(new Point2D.Double(355+i*150,25), cardStack, Type.values()[i], i);
        }



        Stack<Card> foundationStack = new Stack<>();
        foundationStack.addAll(dealingDeck.subList(0,12));
        dealingDeck.subList(0,12).clear();
        foundations[3] = new Foundation(new Point2D.Double(355+3*150,25), foundationStack, Type.values()[3], 3);

        rows[0]  = new Row(new Point2D.Float(130*0 +25,200 ),dealingDeck);

        for (int i = 1; i < rows.length; i++) {
            rows[i] = new Row(new Point2D.Float(130*i +25,200 ),new Stack<>());
        }
        reserve = new Pile(new Point2D.Double(25,25),new Stack<Card>());
        pile = new Pile(new Point2D.Double(155,25),new Stack<Card>());
        System.out.println(deck.size());
        allStocks.add(reserve);
        allStocks.add(pile);
        allStocks.addAll(Arrays.asList(rows));
        allStocks.addAll(Arrays.asList(foundations));

    }



    public ArrayList<Stock> getAllStocks(){ return allStocks;}
    public Stack<Card> getDeck() {
        return deck;
    }

    public Row[] getRows() {
        return rows;
    }

    public Stock getReserve() {
        return reserve;
    }

    public Pile getPile() {
        return pile;
    }

    public Foundation[] getFoundations() {
        return foundations;
    }
}
