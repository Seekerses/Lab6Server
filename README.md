package consolehandler;

import cmd.CommandHistory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Class that works with console input
 */
public class CommandController {
    /**
     * Field which define works this controller or not, sets to true when controller starts and sets to false when its stops
     */
    private boolean isOn;
    /**
     * Field that created to save a history of completed commands
     */
    private  static final CommandHistory commandHistory = new CommandHistory();

    /**
     * Starts the controller. When controller is working user can enter a commands into console.
     * Controller takes command and split it on String array (command and arguments), then throw it to Interpreter
     * @param interpreter Interpreter, which controller will use to interpret commands
     */
    public void start(Interpreter interpreter){
        isOn = true;
        try {

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            registration(interpreter, reader);

            String line = reader.readLine();
            while (isOn){
                if(line == null){
                    System.exit(0);
                 }
                if(!"".equals(line)) {
                    interpreter.handle(line.split(" "));
                    System.out.println("\nEnter command:");
                }
                System.out.print(">");
                line = reader.readLine();
            }
        }
        catch (IOException e){
            System.out.println("Invalid symbol sequence, enter correct command or enter help to get a list of commands...");
        }
    }

    public static void registration(Interpreter interpreter){

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            while (Userfata.login && Userdata.pass == null) {
                System.out.println("Doy you want register or login?\nr - register\nany - login");
                String line = reader.readLine();
                if ("r".equals(line)) {
                    String[] reg = new String[]{"register"};
                    interpreter.handle(reg);
                } else {
                    interpreter.handle(new String[]{"login"});
                }
            }
        } catch (IOException e) {
            System.out.println("Bad input");
            registration(interpreter);
        }
    }

    /**
     * Stops the session of Controller
     */
    public void stop(){
        isOn = false;
    }

    /**
     * Returns the Command History
     * @return Command History
     */
    public static CommandHistory getCommandHistory(){
        return commandHistory;
    }

}
