import BD.DataHandler;
import BD.DataManager;
import BD.DataUserManager;
import consolehandler.*;
import server.ServerController;

import java.io.*;

public class Main {
    private static String databaseHost;
    private static String databasePassword;
    private static String databaseAddress;

    public static void main(String[] args) throws IOException{
        TableManager prodTable = new TableManager("products");
        TableController.setCurrentTable(prodTable);
        try {
            if( new File("saved.csv").createNewFile()){
                System.out.println("Save file created.");
            }
        }
        catch (Exception e ){
            System.out.println("Could not create default save file, please specify it manually\n");
        }
        if(args.length != 0) {
            Initializer.init(prodTable, new File(args[0]));
        }
        else {
            try {
                Initializer.init(prodTable, new File("saved.csv").exists() ? new File("saved.csv") : null);
            } catch (NullPointerException e) {
                Initializer.init(prodTable, null);
            }
        }
        ServerController.connect();
        if (!initialize(args)) return;
        DataHandler databaseHandler = new DataHandler(databaseAddress, "postgres", databasePassword);
        DataUserManager databaseUserManager = new DataUserManager(databaseHandler);
        DataManager dataManager = new DataManager(databaseHandler, databaseUserManager);
        ServerController.start();
        System.out.println("Enter Command or Help to display a list of commands:");
    }

    private static boolean initialize(String[] args) {
        try {
            if (args.length != 2) throw new Exception();
            databaseHost = args[0];
            databasePassword = args[1];
            databaseAddress = "jdbc:postgresql://" + databaseHost + ":5432/unravel";
            return true;
        } catch (Exception e) {
            System.out.println("Input format: Host + Password");
        }
        return false;
    }
}
