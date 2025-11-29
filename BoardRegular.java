/**
 * Provides a regular oval-based visual board design for the Mancala game.
 * This file defines the BoardRegular class, which draws the board using
 * oval pits and mancalas and links their on-screen positions to the
 * underlying pit objects in the model.
 *
 * @author
 * Team: Tazmeen Ahmed, Glengle Pham, Haitham Assaf, Samuel Dinkayehu
 */
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.awt.geom.*;

/**
 * BoardRegular is a concrete BoardDesign implementation that renders
 * the Mancala board with oval pits and mancalas. It also updates each
 * pit's coordinates and shape so the view and model can handle mouse
 * interaction correctly.
 */
public class BoardRegular implements BoardDesign {
	
	private MancalaModel model;
	private ArrayList<Pit> pitList;
    /**
     * Constructs a BoardRegular that uses the given model to retrieve
     * and update the list of pits on the Mancala board.
     *
     * @param model the MancalaModel that maintains the pit list
     */
	public BoardRegular (MancalaModel model) {
		this.model = model;
	}
	
    /**
     * Draws the Mancala board using oval pits for both players and oval
     * mancalas at the sides. This method also stores the screen coordinates
     * and shapes of each pit and mancala in the pit list so the model and
     * view can support user interaction.
     *
     * @param g2 the Graphics2D context used to draw the board
     */
	@Override
public void draw(Graphics2D g2) {
		this.pitList = model.getPitList();
		
		int pitWidth = 80;
        int pitHeight = 120;
        int spacing = 20;
        int mancalaWidth = 80;
        int mancalaHeight = 300;
        int boardX = 300;
        int boardY = 50;

        g2.setColor(new Color(245, 222, 179)); 
        g2.fillRect(boardX, boardY, 900, 600);

        // Player A's pits (bottom row)
        for (int i = 0; i < 6; i++) {
            int x = 150 + i * (pitWidth + spacing) + boardX;
            int y = 400;
            g2.setColor(new Color(210, 180, 140)); // tan pit
            g2.fillOval(x, y, pitWidth, pitHeight);
            g2.setColor(Color.BLACK);
            g2.drawOval(x, y, pitWidth, pitHeight);
            
            // links Player A's pits coordinates so model can access it
            pitList.get(i).setPitXCoordinate(x);
            pitList.get(i).setPitYCoordinate(y);
            Ellipse2D circle = new Ellipse2D.Double(x, y, pitWidth, pitHeight);
            pitList.get(i).setShape(circle);
            
            String label = pitList.get(i).getName();
			g2.drawString(label, x+ 33, y + pitHeight + 15);          
        }

        // Player B's pits (top row)
        for (int i = 0; i < 6; i++) {
            int x = 150 + i * (pitWidth + spacing) + boardX;
            int y = 200;
            g2.setColor(new Color(210, 180, 140));
            g2.fillOval(x, y, pitWidth, pitHeight);
            g2.setColor(Color.BLACK);
            g2.drawOval(x, y, pitWidth, pitHeight);
            
            // links Player B's pits coordinates so model can access it
            int size = pitList.size();
            pitList.get(size - 2 - i).setPitXCoordinate(x);
            pitList.get(size - 2 - i).setPitYCoordinate(y);
            Ellipse2D circle = new Ellipse2D.Double(x, y, pitWidth, pitHeight);
            pitList.get(size - 2 - i).setShape(circle);
            
            String label = pitList.get((size - 7) + i).getName();
            g2.drawString(label, x + 33, y - 10);
        }

        // Player B's Mancala (left side)
        g2.setColor(new Color(160, 82, 45)); 
        g2.fillOval(50 + boardX, 200, mancalaWidth, mancalaHeight);
        g2.setColor(Color.BLACK);
        g2.drawOval(50 + boardX, 200, mancalaWidth, mancalaHeight);
        
        // links Player B's mancala coordinates so model can access it
        pitList.get(13).setPitXCoordinate(50 + boardX);
        pitList.get(13).setPitYCoordinate(200);
        Ellipse2D mancalaA = new Ellipse2D.Double(50 + boardX, 200, mancalaWidth, mancalaHeight);
        pitList.get(13).setShape(mancalaA);
        
        String labelB = pitList.get(13).getName();
        g2.drawString(labelB, 50 + boardX, 200 + mancalaHeight + 20);

        // Player A's Mancala (right side)
        g2.setColor(new Color(160, 82, 45));
        g2.fillOval(150 + 6 * (pitWidth + spacing) + boardX, 200, mancalaWidth, mancalaHeight);
        g2.setColor(Color.BLACK);
        g2.drawOval(150 + 6 * (pitWidth + spacing) + boardX, 200, mancalaWidth, mancalaHeight);
        
        // links Player A's mancala coordinates so model can access it
        pitList.get(6).setPitXCoordinate(150 + 6 * (pitWidth + spacing) + boardX);
        pitList.get(6).setPitYCoordinate(200);
        Ellipse2D mancalaB = new Ellipse2D.Double(
            150 + 6 * (pitWidth + spacing) + boardX,
            200,
            mancalaWidth,
            mancalaHeight
        );
        pitList.get(6).setShape(mancalaB);
        
        String labelA = pitList.get(6).getName();
        g2.drawString(labelA, 150 + 6 * (pitWidth + spacing) + boardX, 200 + mancalaHeight + 20);
	}
}
