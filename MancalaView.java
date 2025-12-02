/**
 * MancalaView provides the graphical user interface for the Mancala game.
 * It displays the initial setup screen, the game board, control buttons,
 * and visual messages to the players as the game progresses.
 *
 * @author
 * Team: Tazmeen Ahmed, Glengle Pham, Haitham Assaf, Samuel Dinkayehu
 * @version 1.0 12/2/25
 */
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.geom.*;
import java.util.ArrayList;

/**
 * The MancalaView class extends JFrame and is responsible for managing
 * the main game window. It creates the initial selection screen,
 * draws the board and stones, and provides buttons and dialogs for
 * player interaction.
 */
public class MancalaView extends JFrame {
	
    JButton gameStartButton = new JButton("Game Start");
    JButton boardRegularButton = new JButton("Regular Board");
    JButton boardHexagonButton = new JButton("Hexagon Board");
    JButton threeStonesButton = new JButton("Three Stones");
    JButton fourStonesButton = new JButton("Four Stones");
    JButton switchPlayerButton = new JButton("Switch Player");
    JButton undoButton = new JButton("Undo Turn");
    private JPanel boardPanel;
    private MancalaModel model;
    
    /**
     * Constructs the MancalaView and displays the initial setup screen where
     * players choose a board design and the number of stones per pit.
     * The view also registers itself with the model.
     *
     * @param model the MancalaModel that this view is associated with
     */
    public MancalaView(MancalaModel model) {
    	
		this.model = model;
		model.addView(this);
    	
        setTitle("Mancala Game");
        setSize(1500, 800);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		
		JPanel chooseDesignPanel = new JPanel();
		chooseDesignPanel.setLayout(new GridLayout(2,2));
		
		ImageIcon imageIcon = new ImageIcon("MancalaRegularImage.png");
		Image image = imageIcon.getImage().getScaledInstance(400, 300, Image.SCALE_SMOOTH);
		JLabel regularBoardPhoto = new JLabel(new ImageIcon(image));
		
		ImageIcon imageIcon2 = new ImageIcon("MancalaHexagonImage.png");
		Image image2 = imageIcon2.getImage().getScaledInstance(400,  300, Image.SCALE_SMOOTH);
		JLabel hexagonBoardPhoto = new JLabel(new ImageIcon(image2));
		
		chooseDesignPanel.add(regularBoardPhoto);
		chooseDesignPanel.add(hexagonBoardPhoto);
		chooseDesignPanel.add(boardRegularButton);
		chooseDesignPanel.add(boardHexagonButton);
		
		JPanel stonesPerPitPanel = new JPanel();
		stonesPerPitPanel.setLayout(new BoxLayout(stonesPerPitPanel, BoxLayout.Y_AXIS));
		JLabel stoneQuestion = new JLabel("How many stones per pit?");
		stonesPerPitPanel.add(stoneQuestion);
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		buttonsPanel.add(threeStonesButton);
		buttonsPanel.add(fourStonesButton);
		stonesPerPitPanel.add(buttonsPanel);
		
		add(chooseDesignPanel, BorderLayout.NORTH);
		add(stonesPerPitPanel, BorderLayout.CENTER);
		add(gameStartButton, BorderLayout.SOUTH);
		
		setVisible(true);
		
    }
    
    /**
     * Updates the main game view after the model changes by rebuilding
     * the top label, board display, and bottom control buttons.
     * This method is typically called by the model when a move is made.
     */
    public void updateView() {
    	
    	getContentPane().removeAll();
    	
    	JPanel stringDisplay = createStringPanel();
    	
    	if (boardPanel == null) {
        	boardPanel = createBoardDisplay();
        }
    	
    	JPanel buttonsDisplay = bottomButtonsPanel();
    	
    	add(stringDisplay, BorderLayout.NORTH);
    	add(boardPanel, BorderLayout.CENTER);
    	add(buttonsDisplay, BorderLayout.SOUTH);
    	
    	revalidate();
    	repaint();
    }
    
    /**
     * Creates the panel that displays whose turn it is at the top of the screen.
     *
     * @return a JPanel containing the current player's turn label
     */
    private JPanel createStringPanel() {
    	JPanel panel = new JPanel();
    	
    	JLabel announceTurn = new JLabel(model.getCurrentPlayer().getName() + "'s Turn");
    	announceTurn.setFont(new Font("SansSerif", Font.PLAIN, 30));
    	panel.add(announceTurn);
 
    	return panel;
    }
    
    /**
     * Creates the main board display panel. It uses a custom paintComponent
     * implementation to draw the board design and stones in each pit
     *
     * @return a JPanel that draws the board 
     */
     public JPanel createBoardDisplay() {
    	JPanel panel = new JPanel() {
    		@Override
    	    public void paintComponent(Graphics g){
    			super.paintComponent(g);
    	        Graphics2D g2 = (Graphics2D) g;
    	        model.getBoardDesign().draw(g2);
    	        
    	        ArrayList<Pit> pitList = model.getPitList();
    	        int width = 8;
    	        int height = 8;
    	        
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
    
    /**
     * Creates the panel that holds the bottom control buttons such as
     * "Switch Player" and "Undo Turn".
     *
     * @return a JPanel containing the bottom row of control buttons
     */
    private JPanel bottomButtonsPanel() {
    	JPanel panel = new JPanel(new GridLayout(2,2));
 
    	panel.add(switchPlayerButton);
    	panel.add(undoButton);
    	
    	return panel;
    }
    
    /**
     * Displays an informational or error message to the players in a
     * dialog box. Used for free turn notifications, invalid moves, and
     * other messages from the model.
     *
     * @param message the text to display in the dialog
     */
    public void visualErrorScreen(String message) {
    	JOptionPane.showMessageDialog(this, message);
    }
    /**
     * Returns board panel
     * 
     * @return a JPanel containing the main board 
     */
    public JPanel getBoardPanel() {
        return boardPanel;
    }
}
