package classificationmodel.bayesiannetwork.graph;

/**
 * Declares a method to calculate the score of an edge linking two nodes.
 */
public interface Score {
    /**
     * Calculates the score of an edge linking two nodes.
     *
     * @param father    Parent node
     * @param son       Child node
     * @return          Score
     */
    double calculate_score(Counts father, Counts son);

}