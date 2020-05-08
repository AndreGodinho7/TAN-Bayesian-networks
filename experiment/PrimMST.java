package experiment;

import project.MyGraph;
import project.MyTreeNode;

import java.util.ArrayList;
import java.util.List;

public class PrimMST {
    private List<FeatureNode> featureNodes;
    private String[] featureNames;

    private int numNodes;
    private double[][] adjMatrix;
    private boolean[] isRoot;
    private int rootIndex = 0;


    // Constructor
    public PrimMST(String[] _featureNames, double[][] _adjMatrix) {
        this.featureNames = _featureNames;
        this.numNodes = _adjMatrix.length;
        this.adjMatrix = _adjMatrix;
        this.featureNodes = new ArrayList<FeatureNode>(this.numNodes);
    }

    // The graph is assumed to be undirected -- symmetric adjacency matrix
    public void defineRoot() {
        boolean notConnected = true;

        for (int line = 0; line < this.numNodes - 1; line++) {
            for (int col = line + 1; col < this.numNodes; col++) {
                if (this.adjMatrix[line][col] != 0) {
                    notConnected = false;
                    break;
                }
            }
            if (!notConnected) {
                break;
            } else {
                this.rootIndex++;
            }
        }
        System.out.println("\nRoot Node: " + this.featureNames[this.rootIndex]);
    }

    // create MyTreeNodes
    private void setFeatureNodes() {
        for (int i = 0; i < this.numNodes; i++) {
            FeatureNode fn = new FeatureNode(this.featureNames[i]);
            this.featureNodes.add(fn);
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

        for (int node = this.rootIndex; node < this.numNodes; node++)
            if (!mstSet[node] && (key[node] > max)) {
                max = key[node];
                max_index = node;
            }
        return max_index;
    }

    /**
     * Compute maximum spanning tree from the graph's adjacency matrix
     */
    public void computeMST() {

        int[] parent = new int[this.numNodes];         // Nodes added to MST
        double[] key = new double[this.numNodes];        // Key values used to pick max weight edge
        Boolean[] mstSet = new Boolean[this.numNodes];

        defineRoot();
        setFeatureNodes();

        for (int i = 0; i < this.numNodes; i++) {
            key[i] = -1;
            mstSet[i] = false;
        }
        key[this.rootIndex] = Double.MAX_VALUE; // Make key MAX so that this node is picked as first node
        parent[this.rootIndex] = -1;            // Root node does not have a parent

        for (int count = this.rootIndex; count < this.numNodes - 1; count++) {
            int u = maxKey(key, mstSet);
            mstSet[u] = true;

            for (int v = this.rootIndex; v < this.numNodes; v++) {
                if ((this.adjMatrix[u][v] != 0) && !mstSet[v] && (this.adjMatrix[u][v] > key[v])) {
                    parent[v] = u;
                    key[v] = this.adjMatrix[u][v];
                }
            }
        }
        /* este ciclo guarda os filhos de cada nó no nó respectivo. Não precisa de percorrer a raíz porque esta não tem
           pai
         */
        for (int i = this.rootIndex + 1; i < this.numNodes; i++) {
            if (parent[i] != -1 && this.adjMatrix[i][parent[i]] > 0) {
                this.featureNodes.get(parent[i])._addChildName(this.featureNames[i]);
            }
        }
        //-------------------------------------------------------------------------------------------------
        // TODO: remove print methods afterwards
        printMST(parent);

        // Print children of each node after running Prim algorithm
        List<FeatureNode> aux = this.featureNodes;
        for (FeatureNode fn : aux) {
            fn.printChildren();
            System.out.println();
        }

    }

    void printMST(int[] parent) {

        System.out.println("Edge \tWeight");
        for (int i = this.rootIndex + 1; i < this.numNodes; i++) {
            if (parent[i] != -1 && this.adjMatrix[i][parent[i]] > 0) {
                System.out.println(parent[i] + 1 + " - " + (i + 1) + "\t" + this.adjMatrix[i][parent[i]]);
            }
        }

    }


    // TODO: Remove this main method (for testing purposes only)
    public static void main(String[] args) {

        String[] features = {"A1", "A2", "A3", "A4", "A5"};
        double[][] adjMatrix = new double[][]{
                {0  ,   0,   0,   0,   0 },
                {0  ,   0,   0,   0,   0 },
                {0  ,   0,   0,   1,   6 },
                {0  ,   0,   1,   0,   6 },
                {0  ,   0,   6,   6,   0 }};

        PrimMST t = new PrimMST(features, adjMatrix);
        t.computeMST();
    }
}
