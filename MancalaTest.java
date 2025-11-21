public class MancalaTest {

    public static void main(String[] args) {
    	
        MancalaModel model = new MancalaModel();
        MancalaView view = new MancalaView(model);
        MancalaController controller = new MancalaController(model, view);
        
        // Test winner identification functionality
        testWinnerIdentification();
    }
    
    /**
     * Test method to demonstrate winner identification functionality
     */
    public static void testWinnerIdentification() {
        System.out.println("Testing Winner Identification Functionality:");
        System.out.println("==========================================");
        
        // Create a new game model
        MancalaModel testModel = new MancalaModel();
        testModel.setStonesPerPit(3);
        testModel.initialize(); // Start with 3 stones per pit
        
        System.out.println("Initial game state:");
        printGameState(testModel);
        
        // Simulate a game ending scenario - empty Player A's side
        for (int i = 0; i <= 5; i++) {
            testModel.getPitList().get(i).setStones(0);
        }
        
        // Give Player A some stones in their Mancala
        testModel.getPitList().get(6).setStones(15);
        
        // Give Player B some stones in their Mancala  
        testModel.getPitList().get(13).setStones(12);
        
        System.out.println("\nGame ending scenario (Player A's side empty):");
        printGameState(testModel);
        
        // Test winner identification
        if (testModel.isGameOver()) {
            System.out.println("\nGame is over!");
            String result = testModel.getGameResult();
            System.out.println(result);
            
            Player winner = testModel.getWinner();
            if (winner != null) {
                System.out.println("Winner: " + winner.getName());
            } else {
                System.out.println("It's a tie!");
            }
        } else {
            System.out.println("\nGame is still in progress");
        }
        
        System.out.println("==========================================");
    }
    
    /**
     * Helper method to print the current game state
     */
    private static void printGameState(MancalaModel model) {
        System.out.println("Player A Score: " + model.getPlayerA().getScore());
        System.out.println("Player B Score: " + model.getPlayerB().getScore());
        System.out.println("Player A's Mancala: " + model.getPitList().get(6).getStones() + " stones");
        System.out.println("Player B's Mancala: " + model.getPitList().get(13).getStones() + " stones");
        
        // Print Player A's pits (A1-A6)
        System.out.print("Player A pits: ");
        for (int i = 0; i <= 5; i++) {
            System.out.print(model.getPitList().get(i).getName() + ":" + model.getPitList().get(i).getStones() + " ");
        }
        System.out.println();
        
        // Print Player B's pits (B1-B6)
        System.out.print("Player B pits: ");
        for (int i = 7; i <= 12; i++) {
            System.out.print(model.getPitList().get(i).getName() + ":" + model.getPitList().get(i).getStones() + " ");
        }
        System.out.println();
    }
}
