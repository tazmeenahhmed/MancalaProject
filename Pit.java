import java.awt.Point;

public class Pit {
	
	private String name;
	private int stones;
	private int x;
	private int y;
	
	public Pit(String name, int stones) {
		
		this.name = name;
		this.stones = stones;
		
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getStones() {
		return this.stones;
	}
	
	public void setStones(int newStones) {
		this.stones = newStones;
	}
	
	
	public void setPitXCoordinate(int x) {
		this.x = x;
	}
	
	public int getPitXCoordinate() {
		return this.x;
	}
	
	public void setPitYCoordinate(int y) {
		this.y = y;
	}
	
	public int getPitYCoordinate() {
		return this.y;
	}
	
	// method to check if point clicked is within a pit
	public boolean containsPoint(Point mousepoint) {
		return false; // note to self: adjust logic !!!
	}
}
