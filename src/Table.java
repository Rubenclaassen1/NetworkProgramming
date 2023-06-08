import java.util.ArrayList;
import java.util.Collections;

public class Table {
    private ArrayList<Card> deck;
    private Row[] rows;
    private ArrayList<Stock> stocks;

    public Table() {
        GenerateCards();
        shuffle();
    }


    private void shuffle(){
        Collections.shuffle(deck);
    }
    private void GenerateCards(){
        deck = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            for (int j = 1; j < 14; j++) {
                deck.add(new Card(j, Type.values()[i],Color.values()[(i/2)], false,null));
            }
        }
    }




    public ArrayList<Card> getDeck() {
        return deck;
    }

    public Row[] getRows() {
        return rows;
    }

    public ArrayList<Stock> getStocks() {
        return stocks;
    }
}
