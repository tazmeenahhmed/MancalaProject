
public class Player {
	
	private String name;
	private boolean currentTurn;
	
	public Player(String name, boolean currentTurn) {
		
		this.name = name;
		this.currentTurn = currentTurn;
		
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
}
