package classificationmodel.bayesiannetwork;


import classificationmodel.ClassificationModel;
import classificationmodel.bayesiannetwork.graph.MyGraph;
import classificationmodel.bayesiannetwork.tree.MaxSpanningTree;
import classificationmodel.bayesiannetwork.tree.MyTreeNode;
import classificationmodel.bayesiannetwork.tree.PrimAlgorithm;
import classificationmodel.bayesiannetwork.tree.TreeNode;
import classificationmodel.bayesiannetwork.graph.WeightedGraph;
import exceptions.IllegalNumberOfClassesException;
import exceptions.IllegalScoreException;
import exceptions.MissingClassInSampleException;
import filehandler.DataReader;
import filehandler.TrainData;
import filehandler.TestData;

import java.util.*;

public class BayesianNetwork extends ClassificationModel {
    private int rootIndex;
    private List<TreeNode> DAG;

    // Getters
//    protected String[] getFeatures() { return features; }
    protected int getRootIndex() { return rootIndex; }
//    public int getNum_classes() { return num_classes; }
    protected TreeNode getDAGNode(int index){ return this.DAG.get(index); }
    // Setters
    private void setDAG(List<TreeNode> DAG) { this.DAG = DAG; }
    protected void setRootIndex(int rootIndex) { this.rootIndex = rootIndex; }


    private double[] calcJointProbability(MyTreeNode node, double[] jointprobabilities, Map <String, Integer> sample){
        if (((TreeNode)node).getisRoot() == false){
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

        DataReader readTrainFile = new TrainData();
        readTrainFile.openFile(trainFilePath);

        // first sweep
        // get feature names
        ((TrainData)readTrainFile).setFeatures();
        ((TrainData)readTrainFile).initr_values();

        // get r values
        try {
            ((TrainData)readTrainFile).setData();
        } catch (IllegalNumberOfClassesException | MissingClassInSampleException e) {
            e.printStackTrace();
            System.exit(1);
        }

        ((TrainData)readTrainFile).print(); // TODO: delete when not needed

        WeightedGraph graph = null; //TODO: duvida, nao deveria ser Graph?
        try {
            graph = new MyGraph((TrainData)readTrainFile, hyperParameter);
        } catch (IllegalScoreException e) {
            e.printStackTrace();
            System.exit(1);
        }
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

        for(TreeNode node : tree){
            node.setData();
        }

        this.setDAG(tree);
        this.setNum_classes(((TrainData)readTrainFile).getNc().length);
        this.setFeatures(((TrainData)readTrainFile).getFeatures());

        int i = 0;
        for (TreeNode node : tree){
            if (((TreeNode)node).getisRoot() == true) setRootIndex(i);
            i++;
        }

        for(TreeNode node : tree){
            System.out.println("Node: " + node.getIdentifier());
            if (((TreeNode) node).getisRoot()) {
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
            System.out.println(String.format("True label: %d | Predicted label: %d", predictions_truelabel[i][0], predictions_truelabel[i][1]));
            i++;
        }

        return predictions_truelabel;
    }

    public void printBayesianNodes() {
        List<TreeNode> tree = this.DAG;
        for (TreeNode tn : tree) {
            if (tn.getisRoot()) {
                System.out.println(String.format("Classifier:\t\t\t\t%s :  ", tn.getIdentifier()));
            } else {
                System.out.println(String.format("\t\t\t\t\t\t%s :  %s",
                        tn.getIdentifier(), tn.getParent().getIdentifier()));
            }

        }
    }
}