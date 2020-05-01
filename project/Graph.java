package project;

import java.util.Map;

public interface Graph {
    public void addEdge(int node1, int node2, float value);
    public void updateNodes(Map<String, Integer> map, int class_value);
    public void setNodes(String []features, int[]r_values, int num_classes);
    public void printNodes();
}
