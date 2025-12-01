/**
 * MancalaModel represents the core game model for the Mancala game.
 * It manages the board pits, players, undo functionality, and game flow,
 * including move logic, captures, extra turns, and detecting when the game ends.
 *
 * @author Team: Tazmeen Ahmed, Glengle Pham, Haitham Assaf, Samuel Dinkayehu
 */
import java.util.ArrayList;
import javax.swing.*;

/**
 * The MancalaModel class stores and updates the state of the Mancala game.
 * It coordinates moves, tracks scores, handles undo operations, and determines
 * when the game is over and who the winner is.
 */
public class MancalaModel {
    
    private ArrayList<Pit> pitList;
    private BoardDesign boardDesign;
    private MancalaView view;
    private Player playerA;
    private Player playerB;
    private UndoManager manager;
    private int stonesPerPit;

    /**
     * Constructs a new MancalaModel, creates two players, and initializes
     * the undo manager. The board is not initialized until {@link #initialize()} is called.
     */
    public MancalaModel() {
        pitList = new ArrayList<>();
        playerA = new Player("Player A", true);
        playerB = new Player("Player B", false);
        manager = new UndoManager(playerA);
        stonesPerPit = 0;
    }

    /**
     * Initializes or resets the board by creating pits for both players,
     * placing the starting number of stones in each regular pit, and clearing
     * the mancalas. It also resets turns and undo counts.
     */
    public void initialize() {
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

    /**
     * Saves the current game state (pits, scores, and active player)
     * into the undo manager so it can be restored later.
     */
    public void saveUndoState() {
        manager.saveState(pitList, playerA.getScore(), playerB.getScore(), playerA.getTurn());
    }

    /**
     * Attempts to undo the last move, restoring a previous game state.
     * If undo is not allowed, an error message is shown on the view.
     *
     * @return the updated list of pits after undo, or the current list
     *         if undo could not be performed
     */
    public ArrayList<Pit> undo() {
        if (isGameOver()) {
            view.visualErrorScreen(getGameResult());
            return pitList;
        }
        if (!getCurrentPlayer().getPlayerMove()) {
            view.visualErrorScreen("No move to undo yet!");
            return pitList;
        }
        ArrayList<Pit> restored = manager.undo(playerA, playerB);
        if (restored != null) {
            pitList = restored;
            syncScoresFromMancalas();
            getCurrentPlayer().didPlayerMove(false);
        } else {
        	view.visualErrorScreen("Reached max number of undos");
        }
        return pitList;
    }

    /**
     * Returns the number of stones assigned to each regular pit at initialization.
     *
     * @return the stones-per-pit value
     */
    public int getStonesPerPit() {
        return this.stonesPerPit;
    }

    /**
     * Sets the number of stones that each regular pit will start with.
     * This should be called before {@link #initialize()}.
     *
     * @param stones the number of stones per pit
     */
    public void setStonesPerPit(int stones) {
        this.stonesPerPit = stones;
    }

    /**
     * Returns the list of pits that make up the current board.
     *
     * @return the current pit list
     */
    public ArrayList<Pit> getPitList() {
        return this.pitList;
    }

    /**
     * Returns the current board design used to draw the Mancala board.
     *
     * @return the board design, or null if none has been set
     */
    public BoardDesign getBoardDesign() {
        return this.boardDesign;
    }

    /**
     * Sets the board design used by the view to draw the Mancala board.
     *
     * @param design the BoardDesign to apply
     */
    public void setBoardDesign(BoardDesign design) {
        this.boardDesign = design;
    }

    /**
     * Registers the view so the model can notify it of changes and display messages.
     *
     * @param mancalaView the view associated with this model
     */
    public void addView(MancalaView mancalaView) {
        this.view = mancalaView;
    }

    /**
     * Executes a move starting from the pit at the given index.
     * This method validates the move, distributes stones, handles captures,
     * checks for extra turns, and determines if the game has ended.
     * It also updates the view and displays relevant messages.
     *
     * @param startIdx the index of the starting pit
     * @return true if the current player earned a free turn, false otherwise
     * @return false if the pit does not belong to the current
     * player, is empty or already selected a pit this turn
     */
    public boolean makeMove(int startIdx) {
        Player current = getCurrentPlayer();
        Player other = (current == playerA) ? playerB : playerA;
        
        if (current.getPlayerMove()) {
            view.visualErrorScreen(current.getName() + " has already selected a pit this turn.");
            return false;
        }
        
        saveUndoState();
        
        if (!isPlayersRegularPit(current, startIdx)) {
        	view.visualErrorScreen("Invalid pit clicked");
            return false;
        }
        
        int stones = pitList.get(startIdx).getStones();
        if (stones == 0) {
        	view.visualErrorScreen("Selected pit is empty.");
            return false;
        }

        pitList.get(startIdx).setStones(0);

        int idx = startIdx;
        int lastIdx = -1;
        while (stones > 0) {
            idx = (idx + 1) % pitList.size();
            if (idx == mancalaIndex(other)) {
                continue;
            }
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
            view.updateView();
            if (view != null) {
                String result = getGameResult();
                view.visualErrorScreen(result);
            }
            return false;
        }
        
        getCurrentPlayer().didPlayerMove(true);

        if (boardDesign != null) {
            view.updateView();
        }
        if (view != null) {
            view.updateView();
        }
        
        manager.clearUndoFlag();
        
        if (freeTurn) {
            manager.resetUndoForFreeTurn();
            getCurrentPlayer().didPlayerMove(false);
            view.visualErrorScreen(getCurrentPlayer().getName() + " gets a free turn!");
            return true;
        } else {
            return false;
        }
    }

    /**
     * Executes a move based on a pit name instead of an index.
     *
     * @param pitName the name of the starting pit (e.g., "A1", "B3")
     * @return false if the pit name is not found
     * @return makeMove(idx) if the current player earned a free turn for true, false otherwise
     */
    public boolean makeMove(String pitName) {
        int idx = indexOf(pitName);
        if (idx < 0) {
            if (view != null) {
                view.visualErrorScreen("Unknown pit: " + pitName);
            }
            return false;
        }
        return makeMove(idx);
    }

    /**
     * Returns the index of the pit with the given name.
     *
     * @param pitName the name of the pit to search for
     * @return the index of the pit, or -1 if not found
     */
    public int indexOf(String pitName) {
        for (int i = 0; i < pitList.size(); i++) {
            if (pitList.get(i).getName().equals(pitName)) return i;
        }
        return -1;
    }

    /**
     * Returns the index of the pit directly opposite the given index.
     * If the index corresponds to a mancala, -1 is returned.
     *
     * @param idx the pit index
     * @return the opposite pit index, or -1 for mancalas
     */
    private int oppositeIndex(int idx) {
        if (idx == 6 || idx == 13) return -1;
        return 12 - idx;
    }

    /**
     * Checks whether a pit index refers to a regular pit belonging to the given player.
     *
     * @param p   the player
     * @param idx the pit index
     * @return true if the pit is a regular pit on that player's side, false otherwise
     */
    private boolean isPlayersRegularPit(Player p, int idx) {
        if (idx < 0 || idx >= pitList.size()) return false;
        if (idx == 6 || idx == 13) return false; 
        if (p == playerA) return idx >= 0 && idx <= 5;
        return idx >= 7 && idx <= 12; 
    }

    /**
     * Returns the index of the mancala pit for the given player.
     *
     * @param p the player
     * @return 6 for player A, 13 for player B
     */
    private int mancalaIndex(Player p) {
        return (p == playerA) ? 6 : 13;
    }

    /**
     * Returns the player whose turn it currently is.
     *
     * @return the active Player
     */
    public Player getCurrentPlayer() {
        return playerA.getTurn() ? playerA : playerB;
    }

    /**
     * Switches the turn from the current player to the other player
     * and resets the undo manager for the new current player.
     */
    public void switchTurn() {
        boolean aTurn = playerA.getTurn();
        playerA.setTurn(!aTurn);
        playerB.setTurn(aTurn);
        manager.resetUndo(getCurrentPlayer());
    }

    /**
     * Syncs the players' scores with the number of stones
     * currently stored in their mancalas.
     */
    public void syncScoresFromMancalas() {
        int manA = pitList.get(6).getStones();
        int manB = pitList.get(13).getStones();
        playerA.setScore(manA);
        playerB.setScore(manB);
    }

    /**
     * Returns player A.
     *
     * @return player A
     */
    public Player getPlayerA() { 
        return playerA; 
    }

    /**
     * Returns player B.
     *
     * @return player B
     */
    public Player getPlayerB() { 
        return playerB; 
    }

    /**
     * Determines whether the game is over, which happens when
     * one player's side of the board is completely empty.
     *
     * @return true if the game is over, false otherwise
     */
    public boolean isGameOver() {
        return isPlayerSideEmpty(playerA) || isPlayerSideEmpty(playerB);
    }

    /**
     * Checks if all regular pits on a given player's side are empty.
     *
     * @param player the player whose side is being checked
     * @return true if all regular pits on that side are empty, false otherwise
     */
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

    /**
     * Ends the game by collecting all remaining stones from both sides
     * and placing them into the corresponding mancalas, then syncing the scores.
     */
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

    /**
     * Returns the winner of the game once it has ended.
     * If the game is tied, or if it is not over yet, null is returned.
     * Note that this method will call {@link #endGame()} if the game is over.
     *
     * @return the winning Player, or null for a tie or if the game is not over
     */
    public Player getWinner() {
        if (!isGameOver()) return null;
        endGame();
        int scoreA = playerA.getScore();
        int scoreB = playerB.getScore();
        if (scoreA > scoreB) return playerA;
        else if (scoreB > scoreA) return playerB;
        else return null;
    }

    /**
     * Returns a human-readable summary of the game result once it is over,
     * including the final scores for both players. If called before the
     * game is finished, a message indicating that the game is still in
     * progress is returned instead.
     *
     * @return a summary string describing the result of the game
     */
    public String getGameResult() {
        if (!isGameOver()) return String.format("Game is still in progress");
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
