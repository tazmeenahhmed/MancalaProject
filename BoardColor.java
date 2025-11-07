import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class BoardColor implements BoardDesign {
	
	private MancalaModel model;
	private ArrayList<Pit> pitList;
	
	public BoardColor (MancalaModel model) {
		this.model = model;
	}
	
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
            pitList.get(i).setPitWidth(pitWidth);
            pitList.get(i).setPitHeight(pitHeight);
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
            pitList.get(i).setPitWidth(pitWidth);
            pitList.get(i).setPitHeight(pitHeight);
        }

        // Player A's Mancala (left side)
        g2.setColor(new Color(160, 82, 45)); 
        g2.fillOval(50 + boardX, 200, mancalaWidth, mancalaHeight);
        g2.setColor(Color.BLACK);
        g2.drawOval(50 + boardX, 200, mancalaWidth, mancalaHeight);
        
        // links Player A's mancala coordinates so model can access it
        pitList.get(6).setPitXCoordinate(50 + boardX);
        pitList.get(6).setPitYCoordinate(200);
        pitList.get(6).setPitWidth(mancalaWidth);
        pitList.get(6).setPitHeight(mancalaHeight);

        // Player B's Mancala (right side)
        g2.setColor(new Color(160, 82, 45));
        g2.fillOval(150 + 6 * (pitWidth + spacing) + boardX, 200, mancalaWidth, mancalaHeight);
        g2.setColor(Color.BLACK);
        g2.drawOval(150 + 6 * (pitWidth + spacing) + boardX, 200, mancalaWidth, mancalaHeight);
        
        // links Player B's mancala coordinates so model can access it
        pitList.get(13).setPitXCoordinate(150 + 6 * (pitWidth + spacing) + boardX);
        pitList.get(13).setPitYCoordinate(200);
        pitList.get(13).setPitWidth(mancalaWidth);
        pitList.get(13).setPitHeight(mancalaHeight);
	}
}
