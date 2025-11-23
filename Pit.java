/**
 * Pit represents a single pit or mancala on the Mancala board.
 * It stores the pit's name, number of stones, screen position,
 * and the shape used for hit detection in the view.
 *
 * @author
 * Team: Tazmeen Ahmed, Glengle Pham, Haitham Assaf, Samuel Dinkayehu
 */
import java.awt.Point;
import java.awt.Shape;

/**
 * The Pit class holds information about a board location in the Mancala game,
 * including its name, stone count, position, and its drawable shape.
 */
public class Pit {
	
	private String name;
	private int stones;
	private int x;
	private int y;
	private Shape shape;

	/**
	 * Creates a pit with the given name and starting stone count.
	 *
	 * @param name   the label for this pit (e.g., "A1", "B3", "MancalaA")
	 * @param stones the initial number of stones in this pit
	 */
	public Pit(String name, int stones) {
		this.name = name;
		this.stones = stones;
	}

	/**
	 * Sets the graphical shape used to represent this pit on the board.
	 *
	 * @param s the Shape associated with this pit
	 */
	public void setShape(Shape s) {
		this.shape = s;
	}

	/**
	 * Sets the x-coordinate of this pit on the screen.
	 *
	 * @param x the x-coordinate of the pit
	 */
	public void setPitXCoordinate(int x) {
		this.x = x;
	}

	/**
	 * Returns the x-coordinate of this pit on the screen.
	 *
	 * @return the x-coordinate of the pit
	 */
	public int getPitXCoordinate() {
		return x;
	}

	/**
	 * Sets the y-coordinate of this pit on the screen.
	 *
	 * @param y the y-coordinate of the pit
	 */
	public void setPitYCoordinate(int y) {
		this.y = y;
	}

	/**
	 * Returns the y-coordinate of this pit on the screen.
	 *
	 * @return the y-coordinate of the pit
	 */
	public int getPitYCoordinate() {
		return y;
	}

	/**
	 * Returns the name of this pit.
	 *
	 * @return this pit's name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Returns the current number of stones in this pit.
	 *
	 * @return the number of stones stored in this pit
	 */
	public int getStones() {
		return this.stones;
	}

	/**
	 * Updates the number of stones stored in this pit.
	 *
	 * @param newStones the new stone count for this pit
	 */
	public void setStones(int newStones) {
		this.stones = newStones;
	}

	/**
	 * Checks whether the given point lies within this pit's shape.
	 * Used by the view to determine which pit was clicked.
	 *
	 * @param mousePoint the point to test, typically from a mouse event
	 * @return true if the point is inside this pit's shape, false otherwise
	 */
	public boolean containsPoint(Point mousePoint) {
		return shape.contains(mousePoint);
	}
}
