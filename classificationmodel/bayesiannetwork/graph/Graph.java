package classificationmodel.bayesiannetwork.graph;

public interface Graph {
    public void setNodes();
    public void printNodes();
    public int numNodes();
    public void createEdge(int node1, int node2);
}
