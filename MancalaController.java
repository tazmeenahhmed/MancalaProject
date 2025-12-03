/**
 * MancalaController wires together the view and the model for the Mancala game.
 * It attaches action listeners to the buttons in the view and triggers updates
 * in the model and view based on user input (board selection, stone count,
 * starting the game, undo, and switching players).
 *
 * @author
 * Team: Tazmeen Ahmed, Glengle Pham, Haitham Assaf, Samuel Dinkayehu
 * @version 1.0 12/4/25
 */
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.*;
import javax.swing.*;

/**
 * The MancalaController class listens for user interactions in the
 * MancalaView and invokes the appropriate methods on the MancalaModel.
 */
public class MancalaController {

    private MancalaView view;
    private MancalaModel model;

    /**
     * Constructs a MancalaController and registers all button listeners
     * on the given view. These listeners update the model and refresh
     * the view as the user configures and plays the game.
     *
     * @param model the MancalaModel that stores game state and logic
     * @param view  the MancalaView that displays the UI and buttons
     */
    public MancalaController(MancalaModel model, MancalaView view) {
        this.model = model;
        this.view = view;

        view.boardRegularButton.addActionListener(new ActionListener() {
            @Override
			/**
			 * Handles the action event triggered when the Regular Board button is clicked.
			 * A button that will display the board game in regular stone pit shapes
			 * Sets the board design to a standard oval layout.
			 * @param e - the action event triggered by clicking the Regular Board button and activates it.
			 */
            public void actionPerformed(ActionEvent e) {
                BoardDesign board = new BoardRegular(model);
                model.setBoardDesign(board);
            }
        });
        
        view.boardHexagonButton.addActionListener(new ActionListener() {
            @Override
			/**
			 * Handles the action event triggered when the Hexagon Board button is clicked.
			 * A button that will display the board game in hexagon stone pit shapes
			 * Sets the board design to a standard hexagonal layout.
			 * @param e - the action event triggered by clicking the Hexagon Board button and activates it.
			 */
            public void actionPerformed(ActionEvent e) {
                BoardDesign board = new BoardHexagon(model);
                model.setBoardDesign(board);
            }
        });
        
        view.threeStonesButton.addActionListener(new ActionListener() {
            @Override
			/**
			 * Handles the action event triggered when the three stone button is clicked.
			 * It makes that all the stone pits except the player's big pits have 3 stones each.
			 * @param e - the action event triggered by clicking the Three Stones button and activates it.
			 */
            public void actionPerformed(ActionEvent e) {
                model.setStonesPerPit(3);
                model.initialize();
            }
        });
        
        view.fourStonesButton.addActionListener(new ActionListener() {
            @Override
			/**
			 * Handles the action event triggered when the four stone button is clicked.
			 * It makes that all the stone pits except the player's big pits have 4 stones each.
			 * @param e - the action event triggered by clicking the Four Stones button and activates it.
			 */
            public void actionPerformed(ActionEvent e) {
                model.setStonesPerPit(4);
                model.initialize();
            }
        });
        
        view.gameStartButton.addActionListener(new ActionListener() {
            @Override
			/**
			 * Handles the action event triggered when the Game start button is clicked.
			 * By pressing the button the game is activated for the players to play the game.
			 * preconditions:
			 * - make sure to choose a board and the stones before starting the game.
			 * - press the Game Start button to play the game.
			 * postconditions:
			 * - The game is ready to be played.
			 * - It will update the game and the board appearance with updateView method.
			 * - the board could display hexagon or oval shape format.
			 * @param e - the action event triggered by clicking the Game Start button and activates it.
			 */
            public void actionPerformed(ActionEvent e) {
                if (model.getBoardDesign() == null) {
                    view.visualErrorScreen("Game view not chosen.");
                    return;
                }
                
                if (model.getStonesPerPit() == 0) {
                    view.visualErrorScreen("Stones per pit not chosen.");
                    return;
                }
                
                view.updateView();
                view.getBoardPanel().addMouseListener(new MouseAdapter() {
                    @Override
					/**
					 * Handles the mouse event triggered when the mouse is pressed to activate it.
					 * preconditions:
					 * - make sure to press your own stone pits, not the other player's stone pits
					 * postconditions:
					 * - the players will move their stones counter clockwise to the pits.
					 * - help notify that the game is over in case the game is done.
					 * - It will update the game and the board appearance with updateView method.
					 * - If the game is over and players continue to click buttons and it will show
					 * - a message that tells that the game is over by showing the winner and scores.
					 * @param e - the mouse event triggered by clicking the board game with mousePressed() and activates it.
					 */
                    public void mousePressed(MouseEvent e) {
                        if (model.isGameOver()) {
                            view.visualErrorScreen(model.getGameResult());
                            return; // stop further processing
                        }
                        Point mousePoint = e.getPoint();
                        for (int i = 0; i < model.getPitList().size(); i++) {
                            if (model.getPitList().get(i).containsPoint(mousePoint)) {
                                try {
                                    model.makeMove(i);
                                } catch (IllegalArgumentException ex) {
                                }
                                view.updateView();
                                break;
                            }
                        }
                    }
                });
            }
        });
        
        view.undoButton.addActionListener(new ActionListener() {
            @Override
			/**
			 * Handles the action event triggered when the Undo Turn button is clicked. 
			 * It allows the current player to undo their turn 3 times.
			 * It will activate the undo() method to ensure that the player can undo their turn.
			 * It will update the game and the board appearance with updateView method.
			 * @param e - the action event triggered by clicking the Undo Turn button and activates it.
			 */
            public void actionPerformed(ActionEvent e) {
                model.undo();
                view.updateView();
            }
        });
        
        view.switchPlayerButton.addActionListener(new ActionListener() {
            @Override
			/**
			 * Handles the action event triggered when the Switch Player button is clicked.
			 * This helps players to take turns to play the game.
			 * It will make sure the players can switch turns when one of them is done.
			 * It will help the next player to get ready for their turn.
			 * It will update the game and the board appearance with updateView method.
			 * preconditions:
			 * - make sure to move one of the stone pits.
			 * - choose the right stone pits.
			 * - then you can press the undo button.
			 * postconditions:
			 * - Undo button is pressed and the next player start their turn.
			 * - if the game is over and players continue to click buttons and it will show
			 * - a message that tells that the game is over by showing the winner and scores.
			 * @param e - the action event triggered by clicking the Switch Player button and activates it.
			 */
            public void actionPerformed(ActionEvent e) {
                if (model.isGameOver()) {
                    view.visualErrorScreen(model.getGameResult());
                    return; 
                }
                if (model.getCurrentPlayer().getPlayerMove() == false) {
                    view.visualErrorScreen("Make a move before switching players.");
                    return;
                }
                
                model.switchTurn();
                model.getCurrentPlayer().didPlayerMove(false);
                view.updateView();
            }
        });
    }
}
