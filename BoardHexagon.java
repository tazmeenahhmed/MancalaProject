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
	}

}
