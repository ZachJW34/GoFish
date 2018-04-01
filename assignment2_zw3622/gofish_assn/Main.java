package gofish_assn;
import java.io.IOException;
import java.util.logging.*;

/**
 * Main that creates a GoFishGame object that implements all the functionality of the Go Fish game, and sets up the file to output the
 * game progression.
 * @see GoFishGame
 */
public class Main {
	
	public static void main(String args[]) {
		setLogger();
		GoFishGame goFishGame = new GoFishGame("Zach", "Eric");
		goFishGame.playGame();
	}

    /**
     * Staic method that sets up the logger. Easier to use then other Filewriters
     */
	private static void setLogger(){
        Logger logger = Logger.getLogger("MyLog");
        FileHandler fh;

        try {
            // This block configure the logger with handler and formatter
            fh = new FileHandler("GoFish_results.txt");
            logger.addHandler(fh);
            //Creates a formatter that is essentially no formatter
            fh.setFormatter(new Formatter() {
                @Override
                public String format(LogRecord record) {
                    return record.getMessage() + "\n";
                }
            });

        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
