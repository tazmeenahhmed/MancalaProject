import java.awt.*;
import java.awt.font.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.geom.*;
import java.util.ArrayList;

public class MancalaView extends JFrame{
	
    private JButton gameStartButton = new JButton("Game Start");
    private JButton boardColorButton = new JButton("Color Board");
    private JButton boardHexagonButton = new JButton("Hexagon Board");
    private JButton threeStonesButton = new JButton("Three Stones");
    private JButton fourStonesButton = new JButton("Four Stones");
    private JButton switchPlayerButton = new JButton("Switch Player");
    private JButton undoButton = new JButton("Undo Turn");
    private JPanel board = new JPanel();
    private MancalaModel model;
    
    // initializes first screen asking for how many stones per pit + which design to choose
    public MancalaView(MancalaModel model) {
    	
    	// view adds itself to model
		this.model = model;
		model.addView(this);
    	
        setTitle("Mancala Game");
        setSize(1500, 800);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		
		
		// panel to allow players to choose a board design
		JPanel chooseDesignPanel = new JPanel();
		chooseDesignPanel.setLayout(new GridLayout(2,2));
		
		ImageIcon imageIcon = new ImageIcon("MancalaColorImage.png");
		Image image = imageIcon.getImage().getScaledInstance(400, 300, Image.SCALE_SMOOTH);
		JLabel colorBoardPhoto = new JLabel(new ImageIcon(image));
	
		JLabel tempPhoto2 = new JLabel("Image of Hexagon Mancala Board");
		chooseDesignPanel.add(colorBoardPhoto);
		chooseDesignPanel.add(tempPhoto2);
		chooseDesignPanel.add(boardColorButton);
		chooseDesignPanel.add(boardHexagonButton);
		
		// panel to allow players to choose 3 or 4 stones per pit
		JPanel stonesPerPitPanel = new JPanel();
		stonesPerPitPanel.setLayout(new BoxLayout(stonesPerPitPanel, BoxLayout.Y_AXIS));
		JLabel stoneQuestion = new JLabel("How many stones per pit?");
		stonesPerPitPanel.add(stoneQuestion);
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		buttonsPanel.add(threeStonesButton);
		buttonsPanel.add(fourStonesButton);
		stonesPerPitPanel.add(buttonsPanel);
		
		// add panels + start game button to JFrame
		add(chooseDesignPanel, BorderLayout.NORTH);
		add(stonesPerPitPanel, BorderLayout.CENTER);
		add(gameStartButton, BorderLayout.SOUTH);
		
		setVisible(true);
		
    }
    
    // repaint screen every time model is changed
    public void updateView() {
    	
    	// remove all elements from the screen
    	getContentPane().removeAll();
    	
    	JPanel stringDisplay = createStringPanel();
    	JPanel boardDisplay = createBoardDisplay();
    	JPanel buttonsDisplay = bottomButtonsPanel();
    	
    	add(stringDisplay, BorderLayout.NORTH);
    	add(boardDisplay, BorderLayout.CENTER);
    	add(buttonsDisplay, BorderLayout.SOUTH);
    	
    	revalidate();
    	repaint();
    }
    
    // panel that "draws" string at the top of the screen
    private JPanel createStringPanel() {
    	JPanel panel = new JPanel();
    	
    	JLabel announceTurn = new JLabel(model.getCurrentPlayer().getName() + "'s Turn");
    	announceTurn.setFont(new Font("SansSerif", Font.PLAIN, 30));
    	panel.add(announceTurn);
 
    	return panel;
    }
    
    // panel that "draws" main board display
    private JPanel createBoardDisplay() {
    	JPanel panel = new JPanel() {
    		@Override
    	    public void paintComponent(Graphics g){
    			super.paintComponent(g);
    	        Graphics2D g2 = (Graphics2D) g;
    	        model.getBoardDesign().draw(g2);
    	        
    	        ArrayList<Pit> pitList = model.getPitList();
    	        int width = 8;
    	        int height = 8;
    	        
    	        // max number of stones that can be a pit is 24 (4 stones x 6 pits)
    	        // so draw them in a graphical way assuming space for 24 stones
    	        for (Pit pit : pitList) {
    	        	int numStones = pit.getStones();
    	        	if (numStones == 0) { continue; }
    	        	
    	        	int firstX = pit.getPitXCoordinate() + 20;
    	        	int y = pit.getPitYCoordinate() + 20;
    	        	
    	        	for (int i = 0; i < 6 && numStones > 0; i++) {
    	        		int x = firstX;
    	        		for (int j = 0; j < 4 && numStones > 0; j++) {
    	        			g2.setColor(Color.BLACK);
    	        			g2.fillOval(x, y, width, height);
    	        			
    	        			x += width + 4;
    	        			numStones--;
    	        		}
    	        		y += height + 4;
    	        	}
    	        }
    	        
    	    }
    	};
    	
    	return panel;
    }
    
    // panel that "draws" buttons at bottom of screen
    private JPanel bottomButtonsPanel() {
    	JPanel panel = new JPanel(new GridLayout(2,2));
 
    	panel.add(switchPlayerButton);
    	panel.add(undoButton);
    	
    	return panel;
    }
    
    // getter methods for MancalaController 
	// I detele the getter method because they are not relevant when I put the names of the Jbutton in the actionlisteners which the code works without the getter methods. - Glengle
}
