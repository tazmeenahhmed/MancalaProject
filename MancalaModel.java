import java.util.ArrayList;

public class MancalaModel {
	
  private int playerAMancala = 0;
  private int playerBMancala = 0;
  private ArrayList<Integer> playerAPits;
  private ArrayList<Integer> playerBPits;
  private BoardDesign design;
  private MancalaView view;
  
  public void initialize(int stonesPerPit) {
	  
  }
  
  public void addPlayerScore(){
	  
  }
  
  public void lostPlayerScore(){
	  
  }
  
  public void undo(){
	  
  }

  
  // method to adds views to model
  public void addView(MancalaView mancalaView) {
	this.view = mancalaView;
  }
}
