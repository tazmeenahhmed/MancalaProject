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
	  // Sync Player scores from Mancala pits (keeps Player.score consistent with board state)
	  syncScoresFromMancalas();
	}
  
	public void lostPlayerScore(){
	  // Same as above; call after captures/score changes to ensure consistency
	  syncScoresFromMancalas();
	}
	
	// should work with undomanager class 
	public void undo(){
	  // hook for your UndoManager; record state snapshots before each move and restore here
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

	/* ===================== Mancala Mechanics (counter-clockwise sowing, free turn, capture, scoring) ===================== */

	/**
	 * Make a move starting from a pit index (0..13) according to the board layout created in initialize().
	 * Enforces: counter-clockwise sowing, skip opponent's Mancala, free turn when last stone lands in own Mancala,
	 * capture when last stone lands in an empty pit on the mover's side (opposite stones + the last one to own Mancala).
	 * Returns true if the current player gets a free turn; false if turn switches.
	 */
	public boolean makeMove(int startIdx) {
		Player current = getCurrentPlayer();
		Player other = (current == playerA) ? playerB : playerA;

		// Validate start pit: must be on current player's side and not a Mancala, and have stones.
		if (!isPlayersRegularPit(current, startIdx)) {
			throw new IllegalArgumentException("Invalid start pit for current player.");
		}
		int stones = pitList.get(startIdx).getStoneCount();
		if (stones == 0) {
			throw new IllegalArgumentException("Selected pit is empty.");
		}

		// Pick up stones.
		pitList.get(startIdx).setStoneCount(0);

		int idx = startIdx;
		int lastIdx = -1;
		while (stones > 0) {
			idx = (idx + 1) % pitList.size();

			// Skip opponent's Mancala.
			if (idx == mancalaIndex(other)) {
				continue;
			}

			pitList.get(idx).setStoneCount(pitList.get(idx).getStoneCount() + 1);
			stones--;
			lastIdx = idx;
		}

		// Check capture: last stone in an empty pit on mover's side (not Mancala).
		if (isPlayersRegularPit(current, lastIdx) && pitList.get(lastIdx).getStoneCount() == 1) {
			int opposite = oppositeIndex(lastIdx);
			int captured = pitList.get(opposite).getStoneCount();
			if (captured > 0) {
				// Move captured + last stone into mover's Mancala.
				int manIdx = mancalaIndex(current);
				int toMove = captured + 1;

				pitList.get(manIdx).setStoneCount(pitList.get(manIdx).getStoneCount() + toMove);
				pitList.get(lastIdx).setStoneCount(0);
				pitList.get(opposite).setStoneCount(0);

				current.addScore(toMove);
			}
		}

		// Free turn if last stone lands in own Mancala.
		boolean freeTurn = (lastIdx == mancalaIndex(current));

		// Synchronize Player scores with Mancala pits to avoid drift.
		syncScoresFromMancalas();

		// Optionally repaint UI if present.
		if (boardDesign != null) {
			boardDesign.repaint();
		}
		if (view != null) {
			view.repaint();
		}

		// Manage turn.
		if (!freeTurn) {
			switchTurn();
		}
		return freeTurn;
	}

	public int indexOf(String pitName) {
		for (int i = 0; i < pitList.size(); i++) {
			if (pitList.get(i).getName().equals(pitName)) return i;
		}
		return -1;
	}

	private int oppositeIndex(int idx) {
		if (idx == 6 || idx == 13) return -1; // Mancalas have no opposite
		return 12 - idx;
	}

	private boolean isPlayersRegularPit(Player p, int idx) {
		if (idx < 0 || idx >= pitList.size()) return false;
		if (idx == 6 || idx == 13) return false; 
		if (p == playerA) {
			return idx >= 0 && idx <= 5;
		} else {
			return idx >= 7 && idx <= 12;
		}
	}

	private int mancalaIndex(Player p) {
		return (p == playerA) ? 6 : 13;
	}

	/** Current player based on the turn flag. */
	public Player getCurrentPlayer() {
		return playerA.getTurn() ? playerA : playerB;
	}

	/** Switch active player turn flags. */
	private void switchTurn() {
		boolean aTurn = playerA.getTurn();
		playerA.setTurn(!aTurn);
		playerB.setTurn(aTurn);
	}

	private void syncScoresFromMancalas() {
		int manA = pitList.get(6).getStoneCount();
		int manB = pitList.get(13).getStoneCount();
		playerA.setScore(manA);
		playerB.setScore(manB);
	}


	public boolean makeMove(String pitName) {
		int idx = indexOf(pitName);
		if (idx < 0) throw new IllegalArgumentException("Unknown pit: " + pitName);
		return makeMove(idx);
	}

	public Player getPlayerA() { return playerA; }
	public Player getPlayerB() { return playerB; }
}
