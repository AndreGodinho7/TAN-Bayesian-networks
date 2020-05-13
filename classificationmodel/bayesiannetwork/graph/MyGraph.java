package classificationmodel.bayesiannetwork.graph;

import filehandler.TrainData;
import exceptions.IllegalScoreException;

import java.util.*;

/**
 * Implements methods to compute a weighted graph.
 */
public class MyGraph extends WeightedGraph {
    private List<Counts> nodes;
    private double[][] adjMatrix;
    private TrainData file;
    private Score score;


    /**
     * Computes a weighted undirected graph given the input training file data and the score metric (LL or MDL).
     *
     * @param graphData                 Input training file data
     * @param score_flag                Score metric (LL or MDL)
     * @throws IllegalScoreException    Exception to be thrown in case the selected score is not LL or MDL
     */
    public MyGraph(TrainData graphData, String score_flag) throws IllegalScoreException {
        super(graphData.getFeatures().length);
        this.file = graphData;
        this.adjMatrix = new double[graphData.getFeatures().length][graphData.getFeatures().length];
        this.nodes = new ArrayList<Counts>(graphData.getFeatures().length);

        if (score_flag.equals("LL")){
            this.score = new LLScore();
        }
        else if (score_flag.equals("MDL")){
            this.score = new MDLScore();
        }
        else {
            throw new IllegalScoreException();
        }
    }

    /**
     * Inserts node in the list of nodes.
     *
     * @param n Node
     */
    private void insertInList(Counts n){
        nodes.add(n);
    }

    /**
     * Returns the list of nodes
     *
     * @return  List of nodes
     */
    public List<Counts> getNodes() {return nodes;}

    /**
     * Returns the adjacency matrix of the graph.
     *
     * @return  Adjacency matrix
     */
    public double[][] getAdjMatrix() {return adjMatrix;}

    /**
     * Returns the input training file data.
     *
     * @return  Input training file data
     */
    public TrainData getFile() { return file; }

    /**
     * Sets the nodes in the graph.
     */
    @Override
    public void setNodes() {
        Node.setfeatures(this.file.getFeatures());
        Node.setR_values(this.file.getR_values());
        Node.setNum_class(this.file.getNum_classes());
        Node.setN(this.file.getN());
        Node.setNc(this.file.getNc());

        for (int i = 0; i < this.getNumNodes(); i++) {
            Counts n = new Node(Node.getFeatures()[i], Node.getR_values()[i], i);
            n.setCounts();
            this.insertInList(n);
        }
    }

    /**
     * Updates the the counts stored in each node
     *
     * @param values    Array of integers corresponding to a new instance
     */
    @Override
    public void updateNodes(int[] values) {
        List<Counts> ns = this.nodes;
        for (Counts n : ns) {
            n.incrementCounts(values);
        }
    }

    /**
     * Returns the number of nodes in the graph.
     *
     * @return  Number of nodes in the graph
     */
    @Override
    public int numNodes() {
        return this.getNumNodes();
    }

    /**
     * Creates an edge linking two nodes (node 1 and node 2).
     *
     * @param node1 Index of node 1
     * @param node2 Index of node 2
     */
    @Override
    public void createEdge(int node1, int node2) {
        double weight = this.score.calculate_score(this.nodes.get(node1), this.nodes.get(node2));
        this.adjMatrix[node1][node2] = weight;
    }

    //TODO: remove print method
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

    //TODO: remove print method
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
