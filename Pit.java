import java.awt.Point;
import java.awt.Shape;

public class Pit {
	
	private String name;
	private int stones;
	private int x;
	private int y;
	private Shape shape;
	
	public Pit(String name, int stones) {
		
		this.name = name;
		this.stones = stones;
		
	}
	
	public void setShape(Shape s) {
		this.shape = s;
	}
	
	public void setPitXCoordinate(int x) {
		this.x = x;
	}
	
	public int getPitXCoordinate() {
		return x;
	}
	
	public void setPitYCoordinate(int y) {
		this.y = y;
	}
	
	public int getPitYCoordinate() {
		return y;
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
	
	public boolean containsPoint(Point mousePoint) {
		return shape.contains(mousePoint);
	}
}
