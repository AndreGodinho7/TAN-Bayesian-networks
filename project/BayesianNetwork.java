package project;

import com.sun.source.tree.Tree;

import javax.print.attribute.standard.PrinterStateReasons;
import java.util.*;

public class BayesianNetwork implements ClassificationModel {

    private String[] features;

    private int num_classes;

    private int rootIndex;

    private List<TreeNode> DAG;

    // Getters
    public String[] getFeatures() { return features; }
    public int getRootIndex() { return rootIndex; }
    public int getNum_classes() { return num_classes; }
    // Setters
    private void setNum_classes(int num_classes) { this.num_classes = num_classes; }
    private void setDAG(List<TreeNode> DAG) { this.DAG = DAG; }
    public void setRootIndex(int rootIndex) { this.rootIndex = rootIndex; }
    public void setFeatures(String[] features) { this.features = features; }

    private TreeNode getDAGNode(int index){
        return this.DAG.get(index);
    }

    private double[] calcJointProbability(MyTreeNode node, double[] jointprobabilities, Map <String, Integer> sample){
        if (node.isRoot == false){
            for (int i=0; i < jointprobabilities.length; i++){
                jointprobabilities[i] = jointprobabilities[i] * node.getThetaijkc(node.getParent().getIdentifier())[sample.get(node.getParent().getIdentifier())][sample.get(node.getIdentifier())][i];
            }
        }

        if (node.getChildren().size() == 0){
            return jointprobabilities;
        }
        else {
            for (TreeNode child : node.getChildren()){
                jointprobabilities = calcJointProbability((MyTreeNode) child, jointprobabilities, sample);
            }
            return jointprobabilities;
        }
    }

    private int classifyInstance(int[] instance){
        String[] features = getFeatures(); //TODO: need feature names
        Map<String, Integer> sample = new HashMap<String, Integer>();
        for (int i = 0; i < instance.length - 1; i++) {
            sample.put(features[i], instance[i]);
        }

        MyTreeNode root = (MyTreeNode) getDAGNode(getRootIndex());
        double[] jointprobabilities = new double[getNum_classes()];

        for (int i=0; i<jointprobabilities.length; i++){
            jointprobabilities[i] = root.getTheta_c()[i]*root.getThetaijkc(root.getIdentifier())[0][sample.get(root.getIdentifier())][i];
        }

        jointprobabilities = calcJointProbability(root, jointprobabilities, sample);
        double sum = Arrays.stream(jointprobabilities).sum();

        int chosen = 0;
        double max = -1;
        for (int i=0; i<jointprobabilities.length;i++){
            double aux = (jointprobabilities[i])/(sum);
            if (max < aux){
                chosen = i;
                max = aux;
            }
        }
        return chosen;
    }

    @Override
    public void train(String trainFilePath, String hyperParameter) {
        int[] values;

        DataReader readTrainFile = new FileData();
        readTrainFile.openFile(trainFilePath);

        // first sweep
        // get feature names
        ((FileData)readTrainFile).setFeatures();
        ((FileData)readTrainFile).initr_values();

        // get r values
        ((FileData)readTrainFile).setData();

        ((FileData)readTrainFile).print(); // TODO: delete when not needed

        WeightedGraph graph = new MyGraph((FileData)readTrainFile, hyperParameter); //TODO: duvida, nao deveria ser Graph?
        graph.setNodes();// TODO: or for loop setNodes(i) ?

        // second sweep
        readTrainFile.openFile(trainFilePath);
        readTrainFile.passline(); // do not read features strings

        // update nodes
        while (true) {
            String line = readTrainFile.readline();
            if (line == null) break;
            values = Arrays.stream(line.split(",")).mapToInt(Integer::parseInt).toArray();
            graph.updateNodes(values);
        }

        // TODO: delete this when not needed anymore
        graph.printNodes();

        for (int i=0; i < graph.numNodes(); i++){
            for (int j=0; j < graph.numNodes();j++){
                if (i==j) continue;
                graph.createEdge(i,j);
            }
        }

        // TODO: delete this when not needed anymore
        ((MyGraph)graph).printadjMatrix();

        // For testing Prim Algorithm
        MaxSpanningTree algorithm = new PrimAlgorithm((MyGraph)graph);
        List<TreeNode> tree = algorithm.computeMST();

        //TODO: apagar nós que não estão na árvore (mas q estão na lista)
        for(TreeNode node : tree){
            node.setData();
        }

        this.setDAG(tree);
        this.setNum_classes(((FileData)readTrainFile).getNc().length);
        this.setFeatures(((FileData)readTrainFile).getFeatures());

        int i = 0;
        for (TreeNode node : tree){
            if (node.isRoot == true) setRootIndex(i);
            i++;
        }

        for(TreeNode node : tree){
            System.out.println("Node: " + node.getIdentifier());
            if (node.equals(algorithm.getRoot())) {
                ((MyTreeNode)node).printTheta_ijkc(node.getIdentifier());
            } else {
                ((MyTreeNode)node).printTheta_ijkc(node.getParent().getIdentifier());
            }
        }
    }

    @Override
    public int[][] predict(String testFilePath) {

        DataReader readTestFile = new TestData<>();
        readTestFile.openFile(testFilePath);
        ((TestData)readTestFile).scanNrLines();
        int i = 0;
        int[][]predictions_truelabel = new int[((TestData)readTestFile).getNrLines()][2];

        readTestFile.openFile(testFilePath);
        readTestFile.passline();

        while (true) {
            String line = readTestFile.readline();
            if (line == null) break;
            int[] sample = Arrays.stream(line.split(",")).mapToInt(Integer::parseInt).toArray();
            predictions_truelabel[i][0] = sample[sample.length-1];
            int chosen = classifyInstance(sample);
            predictions_truelabel[i][1] = chosen;
            System.out.println(String.format("True label: %d | Predicted label: %d",
                                    predictions_truelabel[i][0], predictions_truelabel[i][1]));
            i++;
        }
        return predictions_truelabel;
    }
}