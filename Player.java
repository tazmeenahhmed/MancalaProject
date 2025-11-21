public class Player {
	
	private String name;
	private boolean currentTurn;
	private int numberOfUndos; // meant to keep track of how much undos a player does per turn, should reset to 0 every time switch player button is clicked
	private int score; // player's score
	private boolean move;

	public Player(String name, boolean currentTurn) {
		
		this.name = name;
		this.currentTurn = currentTurn;
		this.numberOfUndos = 0;
		this.score = 0;
		
	}
	
	public String getName() {
		return this.name;
	}
	
	public boolean getTurn() {
		return this.currentTurn;
	}
	
	public void setTurn(boolean turn) {
		this.currentTurn = turn;
	}
	
	public int getNumberOfUndos() {
		return this.numberOfUndos;
	}
	
	public void setNumberOfUndos(int undoIncrement) {
		this.numberOfUndos = undoIncrement;
	}
	
	// check if current player has made a move
	public void didPlayerMove(boolean move) {
		this.move = move;
	}
	
	public boolean getPlayerMove() {
		return move;
	}

	/* ===================== Scoring ===================== */

	public int getScore() {
		return this.score;
	}

	public void addScore(int amount) {
		if (amount < 0) throw new IllegalArgumentException("amount must be >= 0");
		this.score += amount;
	}

	public void decreaseScore(int amount) {
		if (amount < 0) throw new IllegalArgumentException("amount must be >= 0");
		this.score = Math.max(0, this.score - amount);
	}

	public void setScore(int score) {
		if (score < 0) throw new IllegalArgumentException("score must be >= 0");
		this.score = score;
	}

	public void resetScore() {
		this.score = 0;
	}
	
}
