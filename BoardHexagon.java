import java.awt.Graphics2D;
import java.util.ArrayList;

public class BoardHexagon implements BoardDesign{

	private MancalaModel model;
	private ArrayList<Pit> pitList;
	
	public BoardHexagon (MancalaModel model) {
		this.model = model;
	}
	
	@Override
	public void draw(Graphics2D g2) {
		// TODO Auto-generated method stub
		this.pitList = model.getPitList();
		
		int pitWidth = 80;
        int pitHeight = 120;
        int spacing = 20;
        int mancalaWidth = 80;
        int mancalaHeight = 300;
        int boardX = 300;
        int boardY = 50;

		
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
