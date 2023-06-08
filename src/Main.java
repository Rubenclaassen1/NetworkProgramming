import java.awt.geom.Point2D;
import java.sql.SQLOutput;
import java.util.ArrayList;

public class Main {
    private static Table table;
    public static void main(String[] args) {
        table = new Table();
        for (Card card : table.getDeck()) {
            System.out.println(card.toString());
        }
    }





}

