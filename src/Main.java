import java.awt.geom.Point2D;
import java.sql.SQLOutput;
import java.util.ArrayList;

public class Main {
    private static ArrayList<Card> deck;
    public static void main(String[] args) {
        System.out.println(Color.values()[1]);
        GenerateCards();
        for (Card card : deck) {
            System.out.println(card.toString());
        }
    }
    private static void GenerateCards(){
        deck = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            for (int j = 1; j < 14; j++) {
                deck.add(new Card(j, Type.values()[i],Color.values()[(i/2)], false,null));
            }
        }
    }



}

