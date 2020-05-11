package classificationmodel.bayesiannetwork.graph;

public interface WeightedGraph extends Graph {
    public void updateNodes(int[] row);
    public void setNodes();
    public void printNodes();
    public int numNodes();
    public void createEdge(int node1, int node2);
}
