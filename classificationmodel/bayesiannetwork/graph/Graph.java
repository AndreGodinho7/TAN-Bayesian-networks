package classificationmodel.bayesiannetwork.graph;

/**
 * Defines methods to implement a graph.
 */
public interface Graph {

    /**
     * Sets the nodes in the graph.
     */
    public void setNodes();

    //TODO: remove print method before submitting
    public void printNodes();

    /**
     * Returns the number of nodes in the graph.
     * @return  Number of nodes
     */
    public int numNodes();

    /**
     * Creates an edge linking two nodes (node 1 and node 2).
     * @param node1 Index of node 1
     * @param node2 Index of node 2
     */
    public void createEdge(int node1, int node2);
}
