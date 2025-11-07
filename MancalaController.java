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
        
        // we might need this for moving the marbles or dots to different spots
        /*view.(working varible).addMouseListener(new MouseListener() {
             public void mouseClicked(MouseEvent e) {
                
            }
        });
       view.(working varible).addMouseMotionListener(new MouseMotionListener() {
             
            }
        });*/
        
        view.getBoardColorButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BoardDesign board = new BoardColor(model);
				model.setBoardDesign(board);
			}
		});
        
        view.getBoardHexagonButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BoardDesign board = new BoardHexagon(model);
				model.setBoardDesign(board);
			}
		});
        
        view.getThreeStonesButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.initialize(3);
			}
		});
        
        view.getFourStonesButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.initialize(4);
			}
		});
        
        // switches start view to main game view
        view.getGameStartButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				view.updateView();
			}
		});
        
    }
    
    
}

