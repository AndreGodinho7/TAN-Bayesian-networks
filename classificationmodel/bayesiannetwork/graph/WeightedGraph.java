package classificationmodel.bayesiannetwork.graph;

/**
 * Declares methods to implement a weighted graph.
 */
public abstract class WeightedGraph implements Graph{
    private int numNodes;

    public WeightedGraph(int numNodes) {
        this.numNodes = numNodes;
    }

    public int getNumNodes() { return numNodes; }

    /**
     * Updates the counts stored in each node given a new instance.
     *
     * @param row   New instance
     */
    public abstract void updateNodes(int[] row);

    /**
     * Sets the nodes in the graph.
     */
    public abstract void setNodes();

    //TODO: remove print method
    public abstract void printNodes();

    /**
     * Returns the number of nodes in the graph.
     *
     * @return  Number of nodes in the graph
     */
    public abstract int numNodes();

    /**
     * Creates an edge linking two nodes (node 1 and node 2).
     *
     * @param node1 Index of node 1
     * @param node2 Index of node 2
     */
    public abstract void createEdge(int node1, int node2);
}
