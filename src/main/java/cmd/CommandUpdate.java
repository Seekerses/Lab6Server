package cmd;

import consolehandler.Initializer;
import consolehandler.TableController;
import productdata.Product;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

/**
 * Update whole element with given id
 *
 *
 */

public class CommandUpdate implements Command{

    private static final long serialVersionUID = 1337000016L;

    /**
     * Iterates through all elements of collection and update element with given id
     *
     *
     */

    Product product = null;

    @Override
    public String execute(String[] args) {
        System.out.println(Arrays.toString(args));
        try {
            int counter = 0;
            Iterator<Map.Entry<String, Product>> it = TableController.getCurrentTable().getSet().iterator();
            int i = Integer.parseInt(args[0]);
            while (it.hasNext()) {
                Map.Entry<String, Product> map = it.next();
                if (map.getValue().getId() == i) {
                    counter++;
                    product = Initializer.build(args);
                    TableController.getCurrentTable().replace(map.getKey(), product);
                }
            }
            if (counter == 0) {
                return ("There is no elements with that id.");
            }
        } catch (NumberFormatException e) {
            return ("Argument must be a number");
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

}
