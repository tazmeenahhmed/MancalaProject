import java.awt.Graphics2D;

public class BoardColor implements BoardDesign{

	@Override
	public void draw(Graphics2D g2) {
		// I start making a rectangle
		g2.drawRect(50, 50, 100, 75);
	}
}
