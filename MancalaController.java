/**
 * MancalaController wires together the view and the model for the Mancala game.
 * It attaches action listeners to the buttons in the view and triggers updates
 * in the model and view based on user input (board selection, stone count,
 * starting the game, undo, and switching players).
 *
 * @author
 * Team: Tazmeen Ahmed, Glengle Pham, Haitham Assaf, Samuel Dinkayehu
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
            public void actionPerformed(ActionEvent e) {
                BoardDesign board = new BoardRegular(model);
                model.setBoardDesign(board);
            }
        });
        
        view.boardHexagonButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BoardDesign board = new BoardHexagon(model);
                model.setBoardDesign(board);
            }
        });
        
        view.threeStonesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.setStonesPerPit(3);
                model.initialize();
            }
        });
        
        view.fourStonesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.setStonesPerPit(4);
                model.initialize();
            }
        });
        
        view.gameStartButton.addActionListener(new ActionListener() {
            @Override
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
                // now the mouselistener is in controller class for MVC desgin pattern - glengle
                view.getBoardPanel().addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        Point mousePoint = e.getPoint();
                        for (int i = 0; i < model.getPitList().size(); i++) {
                            if (model.getPitList().get(i).containsPoint(mousePoint)) {
                                try {
                                    model.makeMove(i);
                                } catch (IllegalArgumentException ex) {
                                    // model already shows error dialog
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
            public void actionPerformed(ActionEvent e) {
                model.undo();
                view.updateView();
            }
        });
        
        view.switchPlayerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
