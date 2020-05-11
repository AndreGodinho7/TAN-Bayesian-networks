package project;

import java.util.*;

public class MyGraph implements WeightedGraph {
    private List<Counts> nodes;
    private double[][] adjMatrix;
    private int numNodes;
    private FileData file;
    Score score;


    public MyGraph(FileData graphData, String score_flag) {
        this.file = graphData;
        this.adjMatrix = new double[graphData.getFeatures().length][graphData.getFeatures().length];
        this.numNodes = graphData.getFeatures().length;
        this.nodes = new ArrayList<Counts>(graphData.getFeatures().length);

        if (score_flag.equals("LL")){
            this.score = new LLScore();
        }
        else if (score_flag.equals("MDL")){
            this.score = new MDLScore();
        }
        else {
            // Problably insert an exception here
            this.score = null; //TODO: decidir isto
        }
    }

    private void insertInList(Counts n){
        nodes.add(n);
    }

    public List<Counts> getNodes() {return nodes;}

    public double[][] getAdjMatrix() {return adjMatrix;}

    public FileData getFile() { return file; }

    @Override
    public void setNodes() {
        Node.setfeatures(this.file.getFeatures());
        Node.setR_values(this.file.getR_values());
        Node.setNum_class(this.file.getNum_classes());
        Node.setN(this.file.getN());
        Node.setNc(this.file.getNc());

        for (int i = 0; i < this.numNodes; i++) {
            Counts n = new Node(Node.getFeatures()[i], Node.getR_values()[i], i);
            n.setCounts();
            this.insertInList(n);
        }
    }

    @Override
    public void updateNodes(int[] values) {
        List<Counts> ns = this.nodes;
        for (Counts n : ns) {
            n.incrementCounts(values);
        }
    }

    @Override
    public int numNodes() {
        return this.numNodes;
    }

    @Override
    public void createEdge(int node1, int node2) {
        double weight = this.score.calculate_score(this.nodes.get(node1), this.nodes.get(node2));
        this.adjMatrix[node1][node2] = weight;
    }

    @Override
    public void printNodes() {
        List<Counts> ns = this.nodes;
        for (Counts n : ns) {
            System.out.println("Node son: " + ((Node)n).getFeature_name());
            for (String key : ((Node)n).getNijkcMap().keySet()) {
                System.out.println("Node father: " + key);
                System.out.println("Nijkc:");
                ((Node)n).print_Nijkc(key);
                System.out.println("NJjkc:");
                ((Node)n).print_NJikc(key);
                System.out.println("NKjkc:");
                ((Node)n).print_NKijc(key);
            }
        }
    }

    public void printadjMatrix(){
        System.out.println("graph Adjacency matrix");
        for (int i = 0; i < this.adjMatrix.length; i++) {
            for (int j = 0; j < this.adjMatrix.length; j++) {
                System.out.print(this.adjMatrix[i][j] + "\t\t\t");
            }
            System.out.println();
        }
    }
}
