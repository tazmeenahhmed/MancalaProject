/**
 * Provides a hexagon-based visual board design for the Mancala game.
 * This file defines the BoardHexagon class, which draws the board using
 * hexagonal pits and circular mancalas and links their on-screen positions
 * to the underlying pit objects in the model.
 *
 * @author
 * Team: Tazmeen Ahmed, Glengle Pham, Haitham Assaf, Samuel Dinkayehu
 */
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Polygon;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

/**
 * BoardHexagon is a concrete BoardDesign implementation that renders
 * the Mancala board with hexagonal pits and oval mancalas. It also
 * updates each pit's coordinates and shape so the view and model can
 * handle mouse interaction accurately.
 */
public class BoardHexagon implements BoardDesign {

    private MancalaModel model;
    private ArrayList<Pit> pitList;

    /**
     * Constructs a BoardHexagon that uses the given model to retrieve
     * and update the list of pits on the Mancala board.
     *
     * @param model the MancalaModel that maintains the pit list
     */
    public BoardHexagon(MancalaModel model) {
        this.model = model;
    }
    
    /**
     * Draws the Mancala board using hexagonal pits for both players and
     * oval shapes for the mancalas. This method also stores the screen
     * coordinates and shapes of each pit and mancala in the pit list so
     * the model and view can support user interaction.
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
            Polygon hex = createHexagon(x + pitWidth / 2, y + pitHeight / 2, pitWidth, pitHeight);
            g2.setColor(new Color(210, 180, 140));
            g2.fillPolygon(hex);
            g2.setColor(Color.BLACK);
            g2.drawPolygon(hex);
            
            // Links Player A's pits coordinates so model can access them
            pitList.get(i).setPitXCoordinate(x);
            pitList.get(i).setPitYCoordinate(y);
            pitList.get(i).setShape(hex);
            
            String label = pitList.get(i).getName();
            g2.drawString(label, x, y + pitHeight + 10);
        }

        // Player B's pits (top row)
        for (int i = 0; i < 6; i++) {
            int x = 150 + i * (pitWidth + spacing) + boardX;
            int y = 200;
            Polygon hexagon = createHexagon(x + pitWidth / 2, y + pitHeight / 2, pitWidth, pitHeight);
            g2.setColor(new Color(210, 180, 140));
            g2.fillPolygon(hexagon);
            g2.setColor(Color.BLACK);
            g2.drawPolygon(hexagon);
            
            // Links Player B's pits coordinates so model can access them
            int size = pitList.size();
            pitList.get(size - 2 - i).setPitXCoordinate(x);
            pitList.get(size - 2 - i).setPitYCoordinate(y);
            pitList.get(size - 2 - i).setShape(hexagon);
            
            String label = pitList.get(size - 2 - i).getName();
            g2.drawString(label, x, y - 10);
        }
        
        // Player B's Mancala (left side)
        g2.setColor(new Color(160, 82, 45)); 
        g2.fillOval(50 + boardX, 200, mancalaWidth, mancalaHeight);
        g2.setColor(Color.BLACK);
        g2.drawOval(50 + boardX, 200, mancalaWidth, mancalaHeight);
        
        // Links Player B's mancala coordinates so model can access it
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
        
        // Links Player A's mancala coordinates so model can access it
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

    /**
     * Creates a flat-topped hexagon polygon centered at the given coordinates,
     * using the specified width and height.
     *
     * @param centerX the x-coordinate of the center of the hexagon
     * @param centerY the y-coordinate of the center of the hexagon
     * @param width   the overall width of the hexagon
     * @param height  the overall height of the hexagon
     * @return a Polygon representing a six-sided hexagon
     */
    private Polygon createHexagon(int centerX, int centerY, int width, int height) {
        int[] xPoints = new int[6];
        int[] yPoints = new int[6];
    
        for (int i = 0; i < 6; i++) {
            double angle = Math.toRadians(60 * i - 30); // rotate to flat-top
            xPoints[i] = centerX + (int)(width / 2 * Math.cos(angle));
            yPoints[i] = centerY + (int)(height / 2 * Math.sin(angle));
        }
    
        return new Polygon(xPoints, yPoints, 6);
    }
}
