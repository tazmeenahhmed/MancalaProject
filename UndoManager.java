/**
 * UndoManager tracks and restores previous game states for the Mancala game.
 * It keeps a history of board configurations, scores, and turns, and enforces
 * a limit on how many times the current player can undo moves.
 *
 * @author
 * Team: Tazmeen Ahmed, Glengle Pham, Haitham Assaf, Samuel Dinkayehu
 */
import java.util.ArrayList;
import java.util.Stack;

/**
 * The UndoManager class manages the undo stack for Mancala.
 * It stores copies of pit states, player scores, and whose turn it is,
 * and provides methods to save a state, undo it, and reset the undo history.
 */
public class UndoManager {
    private Stack<ArrayList<Pit>> undoStack = new Stack<>();
    private Stack<Integer> mancalaAStack = new Stack<>();
    private Stack<Integer> mancalaBStack = new Stack<>();
    private Stack<Boolean> turnStack = new Stack<>();
    private Player currentPlayer;
    private boolean justUndid = false;

    /**
     * Constructs an UndoManager tied to a specific current player.
     * The current player determines whose undo count is tracked.
     *
     * @param currentPlayer the player whose undos are being counted
     */
    public UndoManager(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    /**
     * Saves a snapshot of the current game state, including a copy of the pit list,
     * both players' scores, and whose turn it is. A state is only saved if the
     * current player has used fewer than three undos and did not just undo.
     *
     * @param currentState the current list of pits representing the board
     * @param scoreA       player A's score at this point
     * @param scoreB       player B's score at this point
     * @param isATurn      true if it is player A's turn, false if it is player B's
     */
    //I change the condition which is cause to undo one time, but now it it undo 3 times per turn - Glengle
    public void saveState(ArrayList<Pit> currentState, int scoreA, int scoreB, boolean isATurn) {
        if (currentPlayer.getNumberOfUndos() < 3) {
            ArrayList<Pit> copy = new ArrayList<>();
            for (Pit pit : currentState) {
                copy.add(new Pit(pit.getName(), pit.getStones()));
            }
            undoStack.push(copy);
            mancalaAStack.push(scoreA);
            mancalaBStack.push(scoreB);
            turnStack.push(isATurn);
        }
    }

    /**
     * Performs an undo operation if allowed. The most recent saved state is popped
     * from the stacks, the scores and turn are restored, and the previous pit list
     * is returned. The current player's undo count is incremented.
     *
     * @param playerA the player object representing player A
     * @param playerB the player object representing player B
     * @return a restored pit list if undo succeeds, or null if undo is not possible
     */
    public ArrayList<Pit> undo(Player playerA, Player playerB) {
        if (canUndo()) {
            currentPlayer.setNumberOfUndos(currentPlayer.getNumberOfUndos() + 1);
            justUndid = true;

            ArrayList<Pit> pits = undoStack.pop();
            int scoreA = mancalaAStack.pop();
            int scoreB = mancalaBStack.pop();
            boolean isATurn = turnStack.pop();

            playerA.setScore(scoreA);
            playerB.setScore(scoreB);

            playerA.setTurn(isATurn);
            playerB.setTurn(!isATurn);

            return pits;
        }
        return null;
    }

    /**
     * Resets the undo history for the next player. This clears all stored states,
     * updates the current player reference, resets the "justUndid" flag, and sets
     * the next player's undo count back to zero.
     *
     * @param nextPlayer the player whose turn will be tracked next
     */
    public void resetUndo(Player nextPlayer) {
        this.currentPlayer = nextPlayer;
        this.justUndid = false;
        this.undoStack.clear();
        this.mancalaAStack.clear();
        this.mancalaBStack.clear();
        this.turnStack.clear();
        nextPlayer.setNumberOfUndos(0);
    }
    /**
     * Resets the undo history for the current player's free turn. This clears all stored states,
     * updates the current player reference, resets the "justUndid" flag, and sets
     * the currents player's undo count back to zero.
     * @param currentPlayer the player who has a free turn to use undo
     */
    public void resetUndoForFreeTurn() {
        this.justUndid = false;
        this.undoStack.clear();
        this.mancalaAStack.clear();
        this.mancalaBStack.clear();
        this.turnStack.clear();
        currentPlayer.setNumberOfUndos(0);
    }
    /**
     * Clears the undo flag for the current player.
     * This method resets the justUndid state to false,
     * indicating that the most recent action was not an undo. It allows
     * subsequent undo operations to be performed again, provided the
     * player has not exceeded the maximum number of undos and the
     * undo stack is not empty.
     */
    public void clearUndoFlag() {
        this.justUndid = false;
    }
    /**
     * Checks whether an undo operation is currently allowed. Undo is permitted
     * only if the current player has used fewer than three undos, did not just
     * undo immediately before, and there is at least one saved state on the stack.
     *
     * @return true if an undo can be performed, false otherwise
     */
    public boolean canUndo() {
        return !justUndid && currentPlayer.getNumberOfUndos() < 3 && !undoStack.isEmpty();
    }
}
