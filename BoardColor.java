import java.awt.Color;
import java.awt.Graphics2D;

public class BoardColor implements BoardDesign{
// I made the board backgorund and format - glengle
	@Override
	public void draw(Graphics2D g2) {
		// I start making a rectangle
		int pitWidth = 80;
        int pitHeight = 120;
        int spacing = 20;
        int mancalaWidth = 80;
        int mancalaHeight = 300;

        g2.setColor(new Color(245, 222, 179)); 
        g2.fillRect(0, 0, 1500, 1500);

        // Player A's pits (bottom row)
        for (int i = 0; i < 6; i++) {
            int x = 150 + i * (pitWidth + spacing);
            int y = 400;
            g2.setColor(new Color(210, 180, 140)); // tan pit
            g2.fillOval(x, y, pitWidth, pitHeight);
            g2.setColor(Color.BLACK);
            g2.drawOval(x, y, pitWidth, pitHeight);
        }

        // Player B's pits (top row)
        for (int i = 0; i < 6; i++) {
            int x = 150 + i * (pitWidth + spacing);
            int y = 200;
            g2.setColor(new Color(210, 180, 140));
            g2.fillOval(x, y, pitWidth, pitHeight);
            g2.setColor(Color.BLACK);
            g2.drawOval(x, y, pitWidth, pitHeight);
        }

        // Player A's Mancala (left side)
        g2.setColor(new Color(160, 82, 45)); 
        g2.fillOval(50, 200, mancalaWidth, mancalaHeight);
        g2.setColor(Color.BLACK);
        g2.drawOval(50, 200, mancalaWidth, mancalaHeight);

        // Player B's Mancala (right side)
        g2.setColor(new Color(160, 82, 45));
        g2.fillOval(150 + 6 * (pitWidth + spacing), 200, mancalaWidth, mancalaHeight);
        g2.setColor(Color.BLACK);
        g2.drawOval(150 + 6 * (pitWidth + spacing), 200, mancalaWidth, mancalaHeight);
	}
}
