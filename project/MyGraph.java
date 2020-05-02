package project;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyGraph implements Graph{
    private List<Node> nodes;
    private float[][] adjMatrix;
    private int numNodes;
    FileData file;


    public MyGraph(FileData graphData) {
        this.file = graphData;
        this.adjMatrix = new float[graphData.getFeatures().length][graphData.getFeatures().length];
        this.numNodes = graphData.getFeatures().length;
        this.nodes = new ArrayList<Node>(graphData.getFeatures().length);
    }

    public void insertInList(Node n){
        nodes.add(n);
    }

    public void setNumNodes(int numNodes) {
        this.numNodes = numNodes;
    }
    @Override
    public void setNodes() {
        String[] features = this.file.getFeatures();
        int[] r_values = this.file.getR_values();
        int num_class = this.file.getNum_classes();
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
            for (String key : n.getNijkc().keySet()) {
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
    public void printNodes() {
        List<Node> ns = this.nodes;
        for (Node n : ns) {
            System.out.println("Node father: " + n.getFeature_name());
            for (String key : n.getNijkc().keySet()) {
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

    @Override
    public int numNodes() {
        return this.numNodes;
    }

    @Override
    public void createEdges() {
        for (int i=0; i < this.numNodes; i++){

            for (int j=i+1; j < this.numNodes;j++){

            }
        }
    }


}
