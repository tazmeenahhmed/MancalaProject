
public class UndoManager {
	/ I added varibles and method that connect to the pits and players - Glengle
	Let me figure out this class. You give me suggestion in discord or comment the ideas
	private Stack<ArrayList<Pit>> undoStack = new Stack<>();
	private Player currentPlayer;
    private boolean justUndid = false;

    public UndoManager(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }
	public void saveState(ArrayList<Pit> currentState) {
		 undoStack.push("working varaible");
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
