import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;

public class MancalaModel {
	
	private ArrayList<Pit> pitList;
	private BoardDesign boardDesign;
	private MancalaView view;
	private Player playerA;
	private Player playerB;
	private UndoManager manager;
	
	public MancalaModel() {
		pitList = new ArrayList<Pit>();
		playerA = new Player("Player A", true);
		playerB = new Player("Player B", false);
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
		playerA.setTurn(true);
	}
  
	public void addPlayerScore(){
	  // keep Player scores consistent with Mancala pit stones
	  syncScoresFromMancalas();
	}
  
	public void lostPlayerScore(){
	  // keep Player scores consistent with Mancala pit stones
	  syncScoresFromMancalas();
	}
	
	// should work with undomanager class 
	public UndoManager undo(){
	  // hook for your UndoManager; capture/restore state before/after makeMove
		return manager;
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

	/* ===================== Mancala mechanics: sowing CCW, free turn, capture, scoring ===================== */

	/**
	 * Perform a move starting from pit index (0..13) using the initialize() layout.
	 * - Sows stones counter-clockwise.
	 * - Skips opponent's Mancala.
	 * - Free turn if last stone lands in current player's Mancala.
	 * - Capture if last stone lands in an empty pit on current player's side: move opposite stones + last stone to player's Mancala.
	 * Returns true if the player gets a free turn; false otherwise (turn switches).
	 */
	
	public boolean makeMove(int startIdx) {
		Player current = getCurrentPlayer();
		Player other = (current == playerA) ? playerB : playerA;

		if (!isPlayersRegularPit(current, startIdx)) {
			throw new IllegalArgumentException("Invalid start pit for current player.");
		}
		int stones = pitList.get(startIdx).getStones();
		if (stones == 0) {
			throw new IllegalArgumentException("Selected pit is empty.");
		}

		pitList.get(startIdx).setStones(0);

		int idx = startIdx;
		int lastIdx = -1;
		while (stones > 0) {
			idx = (idx + 1) % pitList.size();

			if (idx == mancalaIndex(other)) continue;

			pitList.get(idx).setStones(pitList.get(idx).getStones() + 1);
			stones--;
			lastIdx = idx;
		}

		if (isPlayersRegularPit(current, lastIdx) && pitList.get(lastIdx).getStones() == 1) {
			int opp = oppositeIndex(lastIdx);
			int captured = (opp >= 0) ? pitList.get(opp).getStones() : 0;
			if (captured > 0) {
				int man = mancalaIndex(current);
				int toMove = captured + 1; 

				pitList.get(man).setStones(pitList.get(man).getStones() + toMove);
				pitList.get(lastIdx).setStones(0);
				pitList.get(opp).setStones(0);

				current.addScore(toMove);
			}
		}

		boolean freeTurn = (lastIdx == mancalaIndex(current));

		syncScoresFromMancalas();

		// temporarily commenting out for testing purposes bc error 
		// if (boardDesign != null) boardDesign.repaint();
		if (view != null) view.repaint();

		if (!freeTurn) switchTurn();

		return freeTurn;
	}

	public boolean makeMove(String pitName) {
		int idx = indexOf(pitName);
		if (idx < 0) throw new IllegalArgumentException("Unknown pit: " + pitName);
		return makeMove(idx);
	}


	public int indexOf(String pitName) {
		for (int i = 0; i < pitList.size(); i++) {
			if (pitList.get(i).getName().equals(pitName)) return i;
		}
		return -1;
	}

	private int oppositeIndex(int idx) {
		if (idx == 6 || idx == 13) return -1; // Mancalas
		return 12 - idx;
	}

	private boolean isPlayersRegularPit(Player p, int idx) {
		if (idx < 0 || idx >= pitList.size()) return false;
		if (idx == 6 || idx == 13) return false; 
		if (p == playerA) return idx >= 0 && idx <= 5;
		return idx >= 7 && idx <= 12; 
	}

	private int mancalaIndex(Player p) {
		return (p == playerA) ? 6 : 13;
	}

	public Player getCurrentPlayer() {
		return playerA.getTurn() ? playerA : playerB;
	}

	private void switchTurn() {
		boolean aTurn = playerA.getTurn();
		playerA.setTurn(!aTurn);
		playerB.setTurn(aTurn);
		
	}

	private void syncScoresFromMancalas() {
		int manA = pitList.get(6).getStones();
		int manB = pitList.get(13).getStones();
		playerA.setScore(manA);
		playerB.setScore(manB);
	}

	public Player getPlayerA() { return playerA; }
	public Player getPlayerB() { return playerB; }
}
