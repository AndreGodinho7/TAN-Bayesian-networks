package project;

import java.util.LinkedList;

public class MyGraph implements Graph{
    private LinkedList<Node> nodes;
    private float[][] adjMatrix;
    private int numNodes;

    public MyGraph() {
        this.nodes = new LinkedList<Node>();
    }

    public LinkedList<Node> getListNodes() {
        return nodes;
    }

    public void insertInList(Node n){
        nodes.add(n);
    }

    public void setNumNodes(int numNodes) {
        this.numNodes = numNodes;
    }

    @Override
    public void addNode() {

    }

    @Override
    public void addEdge() {

    }
}
