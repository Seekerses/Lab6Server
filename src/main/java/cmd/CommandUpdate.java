package cmd;

import consolehandler.TableController;
import productdata.Product;
import productdata.ReaderProductBuilder;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.Map;

/**
 * Update whole element with given id
 *
 *
 */

public class CommandUpdate implements Command, Preparable{

    private static final long serialVersionUID = 1337000016L;

    /**
     * Iterates through all elements of collection and update element with given id
     *
     *
     */

    Product product = null;
    long id;

    @Override
    public String execute(String[] args) {

        if (product == null){
            this.id= Long.parseLong(args[0]);
            prepare(args);
        }
        else {
            try {
                if (args == null || args[0] == null) {
                    return ("Please enter ID");
                }
                int counter = 0;
                Iterator<Map.Entry<String, Product>> it = TableController.getCurrentTable().getSet().iterator();
                while (it.hasNext()) {
                    Map.Entry<String, Product> map = it.next();
                    if (map.getValue().getId() == id) {
                        counter++;
                        TableController.getCurrentTable().replace(map.getKey(), product);
                    }
                }
                if (counter == 0) {
                    return ("There is no elements with that id.");
                }
            } catch (NumberFormatException e) {
                return ("Argument must be a number");
            }
        }
        return "Element updated";
    }

    /**
     * get name of command
     *
     * @return String
     */

    @Override
    public String toString() {
        return "update_id";
    }

    @Override
    public void prepare(String[] args) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        this.product = ReaderProductBuilder.buildProduct(reader);
    }
}
