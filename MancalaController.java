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

        
        view.boardColorButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BoardDesign board = new BoardColor(model);
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
				model.initialize(3);
			}
		});
        
        view.fourStonesButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.initialize(4);
			}
		});
        
        // switches start view to main game view
        view.gameStartButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
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
				model.switchTurn();
				view.updateView();
			}
		});
    }
    
    
}

