package project;

import java.util.Map;

public interface Graph {
    public void updateNodes(int[] row);
    public void setNodes();
    public void printNodes();
    public int numNodes();
    public void createEdge(int node1, int node2);

    //public int getNumNodes();
    //public int setNode(int index);
    // mudar o nome GraphData -> FileData
    // criar associaçao graphdata -> MyGraph
    // mudar nome updateNodes -> updateNodeWeights

}
