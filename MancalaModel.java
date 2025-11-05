import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;

public class MancalaModel {
	
	private ArrayList<Pit> pitList;
	private BoardDesign boardDesign;
	private MancalaView view;
	private Player playerA;
	private Player playerB;
	
	public MancalaModel() {
		pitList = new ArrayList<Pit>();
		playerA = new Player("PlayerA", true);
		playerB = new Player("PlayerB", false);
	}
	
	public void initialize(int stonesPerPit) {
		pitList.add(new Pit("A1", stonesPerPit));
		pitList.add(new Pit("A2", stonesPerPit));
		pitList.add(new Pit("A3", stonesPerPit));
		pitList.add(new Pit("A4", stonesPerPit));
		pitList.add(new Pit("A5", stonesPerPit));
		pitList.add(new Pit("A6", stonesPerPit));
		pitList.add(new Pit("MancalaA", 0));
		pitList.add(new Pit("B6", stonesPerPit));
		pitList.add(new Pit("B5", stonesPerPit));
		pitList.add(new Pit("B4", stonesPerPit));
		pitList.add(new Pit("B3", stonesPerPit));
		pitList.add(new Pit("B2", stonesPerPit));
		pitList.add(new Pit("B1", stonesPerPit));
		pitList.add(new Pit("MancalaB", 0));
	}
  
	public void addPlayerScore(){
	  
	}
  
	public void lostPlayerScore(){
	  
	}
	
	// should work with undomanager class 
	public void undo(){
	  
	}

	public ArrayList<Pit> getPitList() {
		return this.pitList;
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
