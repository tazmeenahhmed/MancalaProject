public class MancalaTest {
// glengle added these
    public static void main(String[] args) {
        MancalaModel model = new MancalaModel();
        MancalaView view = new MancalaView();
        MancalaController controller = new MancalaController(model, view);
        view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        view.setVisible(true);
    }
}
