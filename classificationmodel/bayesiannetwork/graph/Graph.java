package classificationmodel.bayesiannetwork.graph;

/**
 * Defines methods to implement a graph.
 */
public interface Graph {

    /**
     * Sets the nodes in the graph.
     */
    void setNodes();

    /**
     * Returns the number of nodes in the graph.
     * @return  Number of nodes
     */
    int numNodes();

    /**
     * Creates an edge linking two nodes (node 1 and node 2).
     * @param node1 Index of node 1
     * @param node2 Index of node 2
     */
    void createEdge(int node1, int node2);

    /**
     * Returns an edge value linking two nodes (node 1 and node 2).
     * @param   node1 Index of node 1
     * @param   node2 Index of node 2
     * @return  Edge weight
     */
    double getEdge(int node1, int node2);
}
