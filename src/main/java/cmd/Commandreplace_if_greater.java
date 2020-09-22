package cmd;
import consolehandler.Initializer;
import consolehandler.TableController;
import productdata.Product;
import productdata.ReaderProductBuilder;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

/**
 * replace new product with old one if price of new product is higher
 *
 *
 */

public class Commandreplace_if_greater implements Command,Preparable{

    private static final long serialVersionUID = 1337000014L;

    Product product = null;
    String key = null;

    @Override
    public String execute(String[] args) {
        if (key == null){
            prepare(args);
        }
        try {
            int c = 0;
            for (String key : TableController.getCurrentTable().getKey()) {
                if (key.equals(args[0])) {
                    c++;
                }
            }
            if (c == 0) {
                return ("No such key\nAvailable keys: " + TableController.getCurrentTable().getKey());
            } else {
                for (Map.Entry<String, Product> map : TableController.getCurrentTable().getSet()) {
                    if (map.getKey().compareTo(key) == 0) {
                        if (product != null && product.getPrice() > map.getValue().getPrice()) {
                            TableController.getCurrentTable().replace(map.getKey(), product);
                            return "Element has been replaced";
                        }
                    }
                }
                return ("Empty product. Try again...");
            }
        } catch (NumberFormatException e) {
            return ("Argument must be a number");
        }
    }
    @Override
    public void prepare(String[] args) {
        if (args == null) {
            String key;
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            try {
                do {
                    System.out.println(" Enter product key: ");
                    key = reader.readLine();
                    if (key == null) System.out.println("Error: null key.");
                } while (key == null);
                this.key = key;
                this.product = ReaderProductBuilder.buildProduct(reader);
            } catch (Exception e) {
                System.out.println("Key is null, please try again with valid key...");
            }
        }
        else{
            this.product = Initializer.build(args);
            this.key = args[0];
        }
    }

    @Override
    public String toString() {
        return "replace_if_greater";
    }

}
