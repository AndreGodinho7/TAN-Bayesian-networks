package main;

import project.*;

import java.io.File;
import java.util.*;

public class Main {

    public static void main(String[] args) {

        String trainFilename = "train-enunciado.csv";
        String score = "LL";
        int[] values;

        DataReader readTrainFile = new FileData();
        readTrainFile.openFile(trainFilename);

        // first sweep
        // get feature names
        ((FileData)readTrainFile).setFeatures();
        ((FileData)readTrainFile).initr_values();

        // get r values
        ((FileData)readTrainFile).setData();

        ((FileData)readTrainFile).print(); // TODO: delete when not needed

        WeightedGraph graph = new MyGraph((FileData)readTrainFile, score);
        graph.setNodes(); // TODO: or for loop setNodes(i) ?

        // second sweep
        readTrainFile.openFile(trainFilename);
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
        TreeNode root = algorithm.getRoot();

        //TODO: fazer set para a root
        root.setData();
        for(TreeNode node : tree){
            node.setData();
        }

        for(TreeNode node : tree){
            System.out.println("Father: "+node.getIdentifier());
            List<TreeNode> children = node.getChildren();
            for(TreeNode child : children){
                ((MyTreeNode)node).printTheta_ijkc(child.getIdentifier());
            }
        }

    }
}