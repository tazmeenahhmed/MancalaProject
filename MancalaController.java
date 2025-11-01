import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
             public void mouseDragged(MouseEvent e) {
            }
        });*/
        
        // temporarily commenting this out for testing purposes - tazmeen
        
        view.getBoardColorButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BoardDesign board = new BoardColor();
				model.setBoardDesign(board);
			}
		});
        
        view.getBoardPixelButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BoardDesign board = new BoardPixel();
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
        
        view.getGameStartButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// model method to start game
			}
		});
		// I added this component listener to help with resize the screen in any size. -glengle
        view.addComponentListener(new ComponentAdapter() {
    		@Override
    		public void componentResized(ComponentEvent e) {
        		Dimension newSize = view.getSize();
				Image scaledColor = view.originalColorBoardImage.getImage().getScaledInstance(.getWidth(), .getHeight(), Image.SCALE_SMOOTH);
        		Image scaledPixel = view.originalPixelBoardImage.getImage().getScaledInstance(.getWidth(), .getHeight(), Image.SCALE_SMOOTH);
        		view.tempPhoto1.setIcon(new ImageIcon(scaledColor));
        		view.tempPhoto2.setIcon(new ImageIcon(scaledPixel));
				view.updateView();
			}
    }
    
    
}

