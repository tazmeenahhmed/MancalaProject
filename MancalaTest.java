/**
 * MancalaTest allows to actvate the Mancala game.
 * It launches the GUI and helps with playing the game.
 *
 * @author
 * Team: Tazmeen Ahmed, Glengle Pham, Haitham Assaf, Samuel Dinkayehu
 */
public class MancalaTest {
    /**
     * Entry point for running the Mancala game and the winner test.
     *
     * @param args command-line arguments (unused)
     */
    public static void main(String[] args) {
        MancalaModel model = new MancalaModel();
        MancalaView view = new MancalaView(model);
        MancalaController controller = new MancalaController(model, view);   
    }
