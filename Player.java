
public class Player {
	
	private String name;
	private boolean currentTurn;
	private int numberOfUndos; // meant to keep track of how much undos a player does per turn, should reset to 0 every time switch player button is clicked
	
	public Player(String name, boolean currentTurn) {
		
		this.name = name;
		this.currentTurn = currentTurn;
		this.numberOfUndos = 0;
		
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
	
}
