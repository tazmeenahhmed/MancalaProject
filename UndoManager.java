import java.util.ArrayList;
import java.util.Stack;

public class UndoManager {
    private Stack<ArrayList<Pit>> undoStack = new Stack<>();
    private Stack<Integer> mancalaAStack = new Stack<>();
    private Stack<Integer> mancalaBStack = new Stack<>();
    private Stack<Boolean> turnStack = new Stack<>();
    private Player currentPlayer;
    private boolean justUndid = false;

    public UndoManager(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public void saveState(ArrayList<Pit> currentState, int scoreA, int scoreB, boolean isATurn) {
        if (currentPlayer.getNumberOfUndos() < 3 && !justUndid) {
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

    public void resetUndo(Player nextPlayer) {
        this.currentPlayer = nextPlayer;
        this.justUndid = false;
        this.undoStack.clear();
        this.mancalaAStack.clear();
        this.mancalaBStack.clear();
        this.turnStack.clear();
        nextPlayer.setNumberOfUndos(0);
    }

    public boolean canUndo() {
        return !justUndid && currentPlayer.getNumberOfUndos() < 3 && !undoStack.isEmpty();
    }
}
