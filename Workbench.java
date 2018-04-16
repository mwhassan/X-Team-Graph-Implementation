
public class Workbench {

    public static void main(String[] args) {
        GraphProcessor gp = new GraphProcessor();
        String word1 = "CHARGE";
        String word2 = "GIMLETS";
        
        gp.populateGraph("word_list.txt");
        
        System.out.println("Shortest Distance between Comedo and Charge = " +
                            gp.getShortestDistance(word1, word2));
        
        System.out.println("Shortest Path between Comedo and Charge = " +
                        gp.getShortestPath(word1, word1));
        
        
    }

}

