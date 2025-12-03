/**
 * BoardDesign defines the contract for drawing a Mancala board.
 * Implementations of this interface are responsible for rendering
 * the board layout and its pits using a given graphics context.
 *
 * @author
 * Team: Tazmeen Ahmed, Glengle Pham, Haitham Assaf, Samuel Dinkayehu
 * @version 1.0 12/4/25
 */
import java.awt.Graphics2D;

/**
 * Represents a drawable Mancala board design.
 */
public interface BoardDesign {

    /**
     * Draws the board design using the provided graphics context.
     *
     * @param g2 the Graphics2D context used to render the board
     */
    void draw(Graphics2D g2);
}
