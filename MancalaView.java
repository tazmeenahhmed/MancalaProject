import java.awt.*;
import javax.swing.*;

public class MancalaView extends JFrame{
	
    private JButton gameStartButton = new JButton("Game Start");
    private JButton boardColorButton = new JButton("Color Board");
    private JButton boardPixelButton = new JButton("Pixel Board");
    private JButton threeStonesButton = new JButton("Three Stones");
    private JButton fourStonesButton = new JButton("Four Stones");
    private JPanel board = new JPanel();
    private MancalaModel model;
    
    // initializes first screen asking for how many stones per pit + which design to choose
    public MancalaView(MancalaModel model) {
    	
    	// view adds itself to model
		this.model = model;
		model.addView(this);
    	
        setTitle("Mancala Game");
        setSize(1500,1500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		
		// panel to allow players to choose a board design
		JPanel chooseDesignPanel = new JPanel();
		chooseDesignPanel.setLayout(new GridLayout(2,2));
		// tempPhoto1 & tempPhoto2 should be replaced with actual images of how the boards look like
		// once we have them drawn out
		JLabel tempPhoto1 = new JLabel("Image of Color Mancala Board");
		JLabel tempPhoto2 = new JLabel("Image of Pixel Mancala Board");
		chooseDesignPanel.add(tempPhoto1);
		chooseDesignPanel.add(tempPhoto2);
		chooseDesignPanel.add(boardColorButton);
		chooseDesignPanel.add(boardPixelButton);
		
		// panel to allow players to choose 3 or 4 stones per pit
		JPanel stonesPerPitPanel = new JPanel();
		stonesPerPitPanel.setLayout(new BorderLayout());
		JLabel stoneQuestion = new JLabel("How many stones per pit?");
		stonesPerPitPanel.add(stoneQuestion, BorderLayout.NORTH);
		stonesPerPitPanel.add(threeStonesButton, BorderLayout.WEST);
		stonesPerPitPanel.add(fourStonesButton, BorderLayout.CENTER);
		
		// add panels + start game button to JFrame
		add(chooseDesignPanel, BorderLayout.NORTH);
		add(stonesPerPitPanel, BorderLayout.CENTER);
		add(gameStartButton, BorderLayout.SOUTH);
		
		setVisible(true);
		
    }
    
    // repaint screen every time model is changed
    public void updateView() {
    	
    	
    	
    	
    	
    	repaint();
    }
    
    
    public void paintComponent(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        
    }
}
