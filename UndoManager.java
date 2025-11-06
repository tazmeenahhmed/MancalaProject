
public class UndoManager {
	
	private Player currentPlayer;
    private boolean justUndid = false;

    public UndoManager(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }
}
