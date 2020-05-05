package project;

import java.lang.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PrimAlgorithm  implements MaxSpanningTree{

    private Tree<TreeNode> directedTree;
    TreeNode root;
    private List<TreeNode> treeNodes;

    private MyGraph graph;
    private boolean[] isRoot;

    // Constructor
    public PrimAlgorithm(MyGraph _graph) {
        this.graph = _graph;
        this.treeNodes = new ArrayList<TreeNode>(_graph.file.getFeatures().length);

        // Setting first node as root of the tree
        this.isRoot = new boolean[this.graph.numNodes()];
        Arrays.fill(isRoot, false);
        isRoot[0] = true;
        /* create first TreeNode and add it to the directed tree */
        root = new TreeNode(this.graph.getNodes().get(0));
        directedTree = new Tree<TreeNode>(root);
    }

    // create TreeNodes
    private void setTreeNodes() {
        for (int i = 0; i < this.graph.numNodes(); i++) {
            TreeNode tn = new TreeNode(this.graph.getNodes().get(i));
            this.treeNodes.add(tn);
        }
    }


    /**
     * Find the node with max key value, from the set of nodes not yet in the MST
     * @param key : key value
     * @param mstSet : set of Node in mst (true) or off the mst (false)
     * @return return index of node whose edge has the biggest weight
     */
    private int maxKey(double[] key, Boolean[] mstSet) {
        double max = 0;
        int max_index = -1;

        for (int node = 0; node < this.graph.numNodes(); node++)
            if (!mstSet[node] && (key[node] > max)) {
                max = key[node];
                max_index = node;
            }
        return max_index;
    }

    /**
     * Compute maximum spanning tree from the graph's adjacency matrix
     */
    @Override
    public void computeMST() {

        int[] parent = new int[this.graph.numNodes()];         // Nodes added to MST
        double[] key = new double[this.graph.numNodes()];        // Key values used to pick max weight edge
        Boolean[] mstSet = new Boolean[this.graph.numNodes()];

        setTreeNodes();

        for (int i = 0; i < this.graph.numNodes(); i++) {
            key[i] = -1;
            mstSet[i] = false;
        }

        key[0] = Float.MAX_VALUE; // Make key MAX so that this node is picked as first node
        parent[0] = -1;           // First node is always root of MST

        for (int count = 0; count < this.graph.numNodes() - 1; count++) {
            int u = maxKey(key, mstSet);
            mstSet[u] = true;

            for (int v = count; v < this.graph.numNodes(); v++) {
                if ((this.graph.getAdjMatrix()[u][v] != 0) && !mstSet[v] && (this.graph.getAdjMatrix()[u][v] > key[v])) {
                    parent[v] = u;
                    key[v] = this.graph.getAdjMatrix()[u][v];

                    /*if (isRoot[u]) {
                        // add treeNode[v] as child of the root in directedTree
                        this.directedTree.addChild(this.treeNodes.get(v)); // treeNodes list starts from second node, first node is root
                    } else { }*/


                }
            }
        }
        for (int i = 1; i < this.graph.numNodes(); i++) {
            this.treeNodes.get(parent[i]).addChildName(this.graph.file.getFeatures()[i]);
        }
        //-------------------------------------------------------------------------------------------------
        // TODO: remove print methods afterwards
        printMST(parent);

        // Print children of each node after running Prim algorithm
        List<TreeNode> aux = this.treeNodes;
        for (TreeNode tn : aux) {
            tn.printChildren();
            System.out.println();
        }

    }


    void printMST(int[] parent) {

        System.out.println("Edge \tWeight");
        for (int i = 1; i < this.graph.numNodes(); i++)
            System.out.println(parent[i]+1 + " - " + (i+1) + "\t" + this.graph.getAdjMatrix()[i][parent[i]]);
    }

    // TODO: Remove this main method (for testing purposes only)
    /*public static void main(String[] args) {
        String[] features = {"A1", "A2", "A3", "A4"};
        double[][] adjMatrix = new double[][]{
                {0   , 4.1,  5  ,   0},
                {4.1,    0,  3.8,   0},
                {5   , 3.8,    0, 8.1},
                {0   ,    0, 8.1,   0}};
        String[][] e = new String[adjMatrix.length][2];
        PrimAlgorithm t = new PrimAlgorithm(adjMatrix.length);
        t.computeMST();
        e = t.getEdges();
        System.out.print(Arrays.deepToString(e));
    }*/
}
