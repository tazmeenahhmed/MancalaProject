import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import javax.swing.*;

public class MancalaController{

    private MancalaView view;
    private MancalaModel model;

    public MancalaController(MancalaModel model, MancalaView view) {
        this.model = model;
        this.view = view;

        view.boardRegularButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                BoardDesign board = new BoardRegular(model);
                model.setBoardDesign(board);
            }
        });
        
        view.boardHexagonButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                BoardDesign board = new BoardHexagon(model);
                model.setBoardDesign(board);
            }
        });
        
        view.threeStonesButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	model.setStonesPerPit(3);
                model.initialize();
            }
        });
        
        view.fourStonesButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	model.setStonesPerPit(4);
                model.initialize();
            }
        });
        
        view.gameStartButton.addActionListener(new ActionListener() {
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
            }
        });
        
        view.undoButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                model.undo();
                view.updateView();
            }
        });
        
        view.switchPlayerButton.addActionListener(new ActionListener() {
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
