package project;

import java.io.File;
import java.util.*;

public class MyGraph implements Graph{
    private List<Node> nodes;
    private double[][] adjMatrix;
    private int numNodes;
    FileData file;
    Score score;


    public MyGraph(FileData graphData, String score_flag) {
        this.file = graphData;
        this.adjMatrix = new double[graphData.getFeatures().length][graphData.getFeatures().length];
        this.numNodes = graphData.getFeatures().length;
        this.nodes = new ArrayList<Node>(graphData.getFeatures().length);

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

    private void insertInList(Node n){
        nodes.add(n);
    }

    @Override
    public void setNodes() {
        String[] features = this.file.getFeatures();
        int[] r_values = this.file.getR_values();
        int num_class = this.file.getNum_classes();
        Node.setN(this.file.getN());
        Node.setNc(this.file.getNc());

        for (int i = 0; i < this.numNodes; i++) {
            Node n = new Node(features[i], r_values[i]);
            n.setNijkc(i, features, r_values, num_class);
            n.setNJikc(i, features, r_values, num_class);
            n.setNKijc(i, features, r_values, num_class);
            n.setQ(i, features, r_values[i]);
            n.setR(r_values[i]);
            this.insertInList(n);
        }
    }

    @Override
    public void updateNodes(int[] values) {
        String[] features = this.file.getFeatures();
        int aux_class;
        Map<String, Integer> aux_map = new HashMap<String, Integer>();
        for (int i = 0; i < values.length - 1; i++) {
            aux_map.put(features[i], values[i]);
        }
        aux_class = values[values.length - 1];

        List<Node> ns = this.nodes;
        for (Node n : ns) {
            for (String key : n.getNijkcMap().keySet()) {
                if (n.getFeature_name().equals(key)) {
                    n.inc_Nijkc(key, 0, aux_map.get(key), aux_class);
                    n.inc_NKijc(key,0, aux_class);
                    n.inc_NJikc(key, aux_map.get(key), aux_class);
                } else {
                    n.inc_Nijkc(key, aux_map.get(n.getFeature_name()), aux_map.get(key), aux_class);
                    n.inc_NKijc(key,aux_map.get(n.getFeature_name()), aux_class);
                    n.inc_NJikc(key, aux_map.get(key), aux_class);
                }
            }
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
        List<Node> ns = this.nodes;
        for (Node n : ns) {
            System.out.println("Node father: " + n.getFeature_name());
            for (String key : n.getNijkcMap().keySet()) {
                System.out.println("Node son: " + key);
                System.out.println("Nijkc:");
                n.print_Nijkc(key);
                System.out.println("NJjkc:");
                n.print_NJikc(key);
                System.out.println("NKjkc:");
                n.print_NKijc(key);
            }
        }
    }

    public void printadjMatrix(){
        System.out.println("graph Adjacency matrix");
        for (int i = 0; i < this.adjMatrix.length; i++) {
            for (int j = 0; j < this.adjMatrix.length; j++) {
                System.out.print(this.adjMatrix[i][j] + " ");
            }
            System.out.println();
        }
    }
}
