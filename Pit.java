import java.awt.Point;

public class Pit {
	
	private String name;
	private int stones;
	private int x;
	private int y;
	private int width;
	private int height;
	
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
	
	public void setPitWidth(int width) {
		this.width = width;
	}

	public int getPitWidth() {
		return this.width;
	}
	
	public void setPitHeight(int height) {
		this.width = width;
	}

	public int getPitHeight() {
		return this.height;
	}
	
	// method to check if point clicked is within a pit
	public boolean containsPoint(Point mousepoint) {
		return false; 
	}
}
