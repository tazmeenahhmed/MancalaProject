import java.util.ArrayList;
import javax.swing.*;

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
        manager = new UndoManager(playerA);
    }
    
    public void initialize(int stonesPerPit) {
        pitList.clear();
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
        playerA.setNumberOfUndos(0);
        playerB.setNumberOfUndos(0);
        manager.resetUndo(playerA);
    }

    public void saveUndoState() {
        manager.saveState(pitList, playerA.getScore(), playerB.getScore(), playerA.getTurn());
    }
  
    public ArrayList<Pit> undo() {
        ArrayList<Pit> restored = manager.undo(playerA, playerB);
        if (restored != null) {
            pitList = restored;
            syncScoresFromMancalas();
        }
        return pitList;
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

    public boolean makeMove(int startIdx) {

        saveUndoState();

        Player current = getCurrentPlayer();
        Player other = (current == playerA) ? playerB : playerA;

        if (!isPlayersRegularPit(current, startIdx)) {
        	view.visualErrorScreen("Invalid pit clicked");
            throw new IllegalArgumentException("Invalid start pit for current player.");
        }
        int stones = pitList.get(startIdx).getStones();
        if (stones == 0) {
        	view.visualErrorScreen("Selected pit is empty.");
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

        if (isGameOver()) {
            endGame();
            if (view != null) {
                String result = getGameResult();
                JOptionPane.showMessageDialog(null, result, "Game Over", JOptionPane.INFORMATION_MESSAGE);
            }
        }

        if (boardDesign != null) view.updateView();
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
        if (idx == 6 || idx == 13) return -1;
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

    public void switchTurn() {
        boolean aTurn = playerA.getTurn();
        playerA.setTurn(!aTurn);
        playerB.setTurn(aTurn);
    }

    public void syncScoresFromMancalas() {
        int manA = pitList.get(6).getStones();
        int manB = pitList.get(13).getStones();
        playerA.setScore(manA);
        playerB.setScore(manB);
    }

    public Player getPlayerA() { return playerA; }
    public Player getPlayerB() { return playerB; }

    public boolean isGameOver() {
        return isPlayerSideEmpty(playerA) || isPlayerSideEmpty(playerB);
    }

    private boolean isPlayerSideEmpty(Player player) {
        if (player == playerA) {
            for (int i = 0; i <= 5; i++) {
                if (pitList.get(i).getStones() > 0) return false;
            }
        } else {
            for (int i = 7; i <= 12; i++) {
                if (pitList.get(i).getStones() > 0) return false;
            }
        }
        return true;
    }

    public void endGame() {
        int playerARemaining = 0;
        for (int i = 0; i <= 5; i++) {
            playerARemaining += pitList.get(i).getStones();
            pitList.get(i).setStones(0);
        }
        pitList.get(6).setStones(pitList.get(6).getStones() + playerARemaining);

        int playerBRemaining = 0;
        for (int i = 7; i <= 12; i++) {
            playerBRemaining += pitList.get(i).getStones();
            pitList.get(i).setStones(0);
        }
        pitList.get(13).setStones(pitList.get(13).getStones() + playerBRemaining);

        syncScoresFromMancalas();
    }

    public Player getWinner() {
        if (!isGameOver()) return null;
        endGame();
        int scoreA = playerA.getScore();
        int scoreB = playerB.getScore();
        if (scoreA > scoreB) return playerA;
        else if (scoreB > scoreA) return playerB;
        else return null;
    }

    public String getGameResult() {
        if (!isGameOver()) return "Game is still in progress";
        Player winner = getWinner();
        int scoreA = playerA.getScore();
        int scoreB = playerB.getScore();
        if (winner == null) {
            return String.format("It's a tie! Final scores - %s: %d, %s: %d", 
                playerA.getName(), scoreA, playerB.getName(), scoreB);
        } else {
            return String.format("%s wins! Final scores - %s: %d, %s: %d", 
                winner.getName(), playerA.getName(), scoreA, playerB.getName(), scoreB);
        }
    }
}
