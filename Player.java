/**
 * Player represents a single player in the Mancala game.
 * It tracks the player's name, turn status, undo count, score,
 * and whether the player has made a move during the current turn.
 *
 * @author
 * Team: Tazmeen Ahmed, Glengle Pham, Haitham Assaf, Samuel Dinkayehu
 * @version 1.0 12/2/25
 */
/**
 * The Player class stores information related to a player's state,
 * including their score, current turn, number of undos used, and
 * whether they have made a move this turn.
 */
public class Player {
	
	private String name;
	private boolean currentTurn;
	private int numberOfUndos; 
	private int score; 
	private boolean move;

	/**
	 * Constructs a new Player with the given name and initial turn status.
	 *
	 * @param name the player's name
	 * @param currentTurn true if this player starts with the turn, false otherwise
	 */
	public Player(String name, boolean currentTurn) {
		this.name = name;
		this.currentTurn = currentTurn;
		this.numberOfUndos = 0;
		this.score = 0;
	}
	
	/**
	 * Returns the player's name.
	 *
	 * @return the name of the player
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Returns whether it is currently this player's turn.
	 *
	 * @return true if it is this player's turn, false otherwise
	 */
	public boolean getTurn() {
		return this.currentTurn;
	}
	
	/**
	 * Sets whether it is this player's turn.
	 *
	 * @param turn true to give this player the turn, false otherwise
	 */
	public void setTurn(boolean turn) {
		this.currentTurn = turn;
	}
	
	/**
	 * Returns the number of undos used by this player in the current turn.
	 *
	 * @return the number of undos
	 */
	public int getNumberOfUndos() {
		return this.numberOfUndos;
	}
	
	/**
	 * Sets the number of undos used by this player in the current turn.
	 *
	 * @param undoIncrement the new undo count
	 */
	public void setNumberOfUndos(int undoIncrement) {
		this.numberOfUndos = undoIncrement;
	}
	
	/**
	 * Records whether the current player has made a move during this turn.
	 *
	 * @param move true if the player has made a move, false otherwise
	 */
	public void didPlayerMove(boolean move) {
		this.move = move;
	}
	
	/**
	 * Returns whether the player has made a move during this turn.
	 *
	 * @return true if the player has made a move, false otherwise
	 */
	public boolean getPlayerMove() {
		return move;
	}

	/* ===================== Scoring ===================== */

	/**
	 * Returns the player's current score.
	 *
	 * @return the score for this player
	 */
	public int getScore() {
		return this.score;
	}

	/**
	 * Increases the player's score by the specified amount.
	 *
	 * @param amount the number of points to add (must be non-negative)
	 * @throws IllegalArgumentException if amount is negative
	 */
	public void addScore(int amount) {
		if (amount < 0) throw new IllegalArgumentException("amount must be >= 0");
		this.score += amount;
	}

	/**
	 * Decreases the player's score by the specified amount, but not below zero.
	 *
	 * @param amount the number of points to subtract (must be non-negative)
	 * @throws IllegalArgumentException if amount is negative
	 */
	public void decreaseScore(int amount) {
		if (amount < 0) throw new IllegalArgumentException("amount must be >= 0");
		this.score = Math.max(0, this.score - amount);
	}

	/**
	 * Sets the player's score to the specified value.
	 *
	 * @param score the new score value (must be non-negative)
	 * @throws IllegalArgumentException if score is negative
	 */
	public void setScore(int score) {
		if (score < 0) throw new IllegalArgumentException("score must be >= 0");
		this.score = score;
	}

	/**
	 * Resets the player's score to zero.
	 */
	public void resetScore() {
		this.score = 0;
	}
}
