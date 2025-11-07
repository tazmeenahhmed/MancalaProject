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
	

}
