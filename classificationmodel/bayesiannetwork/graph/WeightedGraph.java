package classificationmodel.bayesiannetwork.graph;

public abstract class WeightedGraph implements Graph{
    private int numNodes;

    public WeightedGraph(int numNodes) {
        this.numNodes = numNodes;
    }

    public int getNumNodes() { return numNodes; }

    public abstract void updateNodes(int[] row);
    public abstract void setNodes();
    public abstract void printNodes();
    public abstract int numNodes();
    public abstract void createEdge(int node1, int node2);
}
