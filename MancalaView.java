import java.awt.*;
import javax.swing.*;

public class MancalaView extends JFrame{
	
    private JButton addButton = new JButton("Game Start");
    private JPanel board = new JPanel();
    private MancalaModel model;
    
    // initializes first screen asking for how many stones per pit + which design to choose
    public MancalaView(MancalaModel model) {
    	
    	// adds itself to model
		this.model = model;
		model.addView(this);
    	
        setTitle("Mancala Game");
    }
    
    // repaint screen every time model is changed
    public void updateView() {
    	
    }
    
    
    public void paintComponent(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        
    }
}
