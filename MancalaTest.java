/**
 * MancalaTest allows to actvate the Mancala game.
 * It launches the GUI and helps with playing the game.
 *
 * @author
 * Team: Tazmeen Ahmed, Glengle Pham, Haitham Assaf, Samuel Dinkayehu
 */
public class MancalaTest {
    /**
     * Entry point for running the Mancala game.
     * It makes sure the players get to play the game.
     * @param args command-line arguments (unused)
     */
    public static void main(String[] args) {
        MancalaModel model = new MancalaModel();
        MancalaView view = new MancalaView(model);
        MancalaController controller = new MancalaController(model, view);   
    }
