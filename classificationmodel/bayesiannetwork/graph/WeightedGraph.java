package classificationmodel.bayesiannetwork.graph;

/**
 * Declares methods to implement a weighted graph.
 * Contains a variable with the number of nodes in the graph.
 */
public abstract class WeightedGraph implements Graph{
    protected int numNodes;

    /**
     * Created an object WeightedGraph and initializes it with the number of nodes of the graph
     * @param numNodes Number of nodes in the graph
     */
    public WeightedGraph(int numNodes) {
        this.numNodes = numNodes;
    }

    /**
     * Returns the number of nodes of the graph
     * @return
     */
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

    /**
     * Returns an edge value linking two nodes (node 1 and node 2).
     *
     * @param node1 Index of node 1
     * @param node2 Index of node 2
     */
    public abstract double getEdge(int node1, int node2);
}
