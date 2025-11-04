import java.util.ArrayList;

import javax.swing.JButton;

public class MancalaModel {
	
	private int playerAMancala = 0;
	private int playerBMancala = 0;
	private ArrayList<Integer> playerAPits;
	private ArrayList<Integer> playerBPits;
	private BoardDesign boardDesign;
	private MancalaView view;
	
	// add # of stonesPerPit to each pit for playerAPits and playerBPits (6 pits total for each)
	public void initialize(int stonesPerPit) {
		playerAPits = new ArrayList<>();
    	playerBPits = new ArrayList<>();
    	for (int i = 0; i < 6; i++) {
        	playerAPits.add(stonesPerPit);
        	playerBPits.add(stonesPerPit);
    	}
    	playerAMancala = 0;
    	playerBMancala = 0;
	}
  
	public void addPlayerScore(){
	  
	}
  
	public void lostPlayerScore(){
	  
	}
  
	public void undo(){
	  
	}

	
	// getter & necessary setter methods
	public ArrayList<Integer> getPlayerAPits() {
		return this.playerAPits;
	}
	
	public ArrayList<Integer> getPlayerBPits() {
		return this.playerBPits;
	}
	
	public int getPlayerAMancala() {
		return this.playerAMancala;
	}
	
	public int getPlayerBMancala() {
		return this.playerBMancala;
	}
	
	public BoardDesign getBoardDesign() {
		return this.boardDesign;
	}
	
	public void setBoardDesign(BoardDesign design) {
		this.boardDesign = design;
	}
	
	public void addView(MancalaView mancalaView) {
		this.view = mancalaView;
	}
	
}
