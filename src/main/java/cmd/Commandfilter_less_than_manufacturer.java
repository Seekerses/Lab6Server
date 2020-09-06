package cmd;

import Control.TableController;
import productdata.Product;

import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;

/**
 * get elements which have lower manufacturer id than one's given
 *
 *
 */

public class Commandfilter_less_than_manufacturer implements Command {
    /**
     * counter will control a situation when all elements have higher manufacturer id than one's given
     */
    private static final long serialVersionUID = 1337000006L;

    class ShowInfo{
        private String info = "";

        public void addInfo(String info){
            this.info += info;
        }

        public String getInfo(){
            return info;
        }
    }

    @Override
    public String execute(String[] args) {
        try {
            ShowInfo showInfo = new ShowInfo();
            TableController.getCurrentTable().getProducts().stream().filter(p -> p.getManufacturer().getFullName().compareTo(Arrays.toString(args)) == 0)
                    .sorted(Comparator.comparing(p -> p.getManufacturer().getFullName()))
                    .forEach(x -> showInfo.addInfo(x.out()));

            if (showInfo.getInfo().equals("")) {
                return ("No such elements.");
            }
            return showInfo.getInfo();
        }catch(NumberFormatException e){
            return ("Argument must be a number!");
        }
    }

    /**
     * get name of command
     *
     * @return String
     */

    @Override
    public String toString() {
        return "filter_less_than_manufacturer";
    }
}
