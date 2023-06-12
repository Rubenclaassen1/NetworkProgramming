import sun.plugin2.liveconnect.RemoteJavaObject;

import java.util.*;

public class Main {
    private static Table table;
    public static void main(String[] args) {
        table = new Table(null);

        System.out.println("Stock: " + table.getStock());
        System.out.println("Pile: " + table.getPile());
        int i = 1;
        for (Row row : table.getRows()) {
            System.out.println("Row " + i +": "+ row);
            i++;
        }
        i = 1;
        for (Foundation foundation : table.getFoundations()) {
            System.out.println("Foundation " + i +": "+ foundation);
            i++;
        }


    }


}

