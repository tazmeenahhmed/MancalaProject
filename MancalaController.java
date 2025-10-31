public class MancalaController{

    private MancalaView view;
    private MancalaModel model;

    public MancalaController(MancalaModel model, MancalaView view) {
        this.model = model;
        this.view = view;
        
        // we might need this for moving the marbles or dots to different spots
        // temporarily commenting this out for testing purposes - tazmeen
        /*view.(working varible).addMouseListener(new MouseListener() {
             public void mouseClicked(MouseEvent e) {
                
            }
        });
       view.(working varible).addMouseMotionListener(new MouseMotionListener() {
             public void mouseDragged(MouseEvent e) {
            }
        });*/
    }
}

