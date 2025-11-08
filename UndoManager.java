import java.util.ArrayList;
import java.util.Stack;

public class UndoManager {
	// I added varibles and method that connect to the pits and players - Glengle
	//Let me figure out this class. You give me suggestion in discord or comment the ideas
	private Stack<ArrayList<Pit>> undoStack = new Stack<>();
	private Player currentPlayer;
    private boolean justUndid = false;

    public UndoManager(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }
	public void saveState(ArrayList<Pit> currentState) {
		 if (currentPlayer.getNumberOfUndos() < 3 && !justUndid) {
            ArrayList<Pit> copy = new ArrayList<>();
            for (Pit pit : currentState) {
                copy.add(new Pit(pit.getName(), pit.getStones()));
            }
            undoStack.push(copy);
        }
	}
	public ArrayList<Pit> undo(){
		if (canUndo()) {
            currentPlayer.setNumberOfUndos(currentPlayer.getNumberOfUndos() + 1);
            justUndid = true;
            return undoStack.pop();
        }
        return null;
	}
	public void resetUndo(Player nextPlayer) {
		this.currentPlayer = nextPlayer;
        this.justUndid = false;
        this.undoStack.clear();
        nextPlayer.setNumberOfUndos(0);
	}
	public boolean canUndo() {
        return !justUndid && currentPlayer.getNumberOfUndos() < 3 && !undoStack.isEmpty();
    }
}
