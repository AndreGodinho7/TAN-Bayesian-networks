package project;

import java.util.*;
import java.lang.*;
import java.io.*;

public class MaximumSpanningTree {

    private String[][] edges;
    private int numNodes; // Number of nodes in the graph

    // Constructor
    public MaximumSpanningTree(int _nrNodes) {
        this.numNodes = _nrNodes;
        this.edges = new String[_nrNodes - 1][2];
    }

    // Getter methods:
    public String[][] getEdges() {return this.edges;}

    // Find the node with max key value, from the set of nodes not yet in the MST
    private int maxKey(float[] key, Boolean[] mstSet) {
        float max = 0;
        int max_index = -1;

        for (int node = 0; node < this.numNodes; node++)
            if (!mstSet[node] && (key[node] > max)) {
                max = key[node];
                max_index = node;
            }
        return max_index;
    }

    /**
     * Compute maximum spanning tree from the graph's adjacency matrix
     *
     * @param adjMatrix : graph's adjacency matrix
     */
    public void computePrimMST(float[][] adjMatrix, String[] featureLabels) {
        int[] parent = new int[this.numNodes];         // Nodes added to MST
        float[] key = new float[this.numNodes];        // Key values used to pick max weight edge
        Boolean[] mstSet = new Boolean[this.numNodes];

        for (int i = 0; i < this.numNodes; i++) {
            key[i] = -1;
            mstSet[i] = false;
        }

        key[0] = Float.MAX_VALUE; // Make key MAX so that this node is picked as first node
        parent[0] = -1;           // First node is always root of MST

        for (int count = 0; count < this.numNodes - 1; count++) {
            int u = maxKey(key, mstSet);
            mstSet[u] = true;

            int v_value = -1;
            for (int v = 0; v < this.numNodes; v++) {
                if ((adjMatrix[u][v] != 0) && !mstSet[v] && (adjMatrix[u][v] > key[v])) {
                    parent[v] = u;
                    key[v] = adjMatrix[u][v];
                }
            }
        }
        for (int i = 1; i < this.numNodes; i++) {
            String edgeParent = featureLabels[parent[i]]; //"A" + (parent[i] + 1);
            String edgeChild = featureLabels[i]; //"A" + (i + 1);
            if (i < parent[i]) {
                edgeParent = featureLabels[i]; //"A" + String.valueOf(i+1);
                edgeChild = featureLabels[parent[i]]; //"A" + String.valueOf(parent[i] + 1);
            }
            String[] edge = {edgeParent, edgeChild};
            this.edges[i - 1] = edge;
        }
        // TODO: remove print methods afterwords
        printMST(parent, adjMatrix);
    }

    void printMST(int[] parent, float[][] adjMatrix) {

        System.out.println("Edge \tWeight");
        for (int i = 1; i < this.numNodes; i++)
            System.out.println(parent[i]+1 + " - " + (i+1) + "\t" + adjMatrix[i][parent[i]]);
    }

    /*public void printEdge(String key) {
        String value = this.edges.get(key);
        System.out.println(key + "--" + value);
    }*/

    // TODO: Remove this main method (for testing purposes only)
    public static void main(String[] args) {
        String[] features = {"A1", "A2", "A3"};
        float[][] adjMatrix = new float[][]{
                {0, 2.1f, 5   },
                {2.1f, 0, 3.8f},
                {5, 3.8f, 0   }};
        String[][] e = new String[adjMatrix.length][2];
        MaximumSpanningTree t = new MaximumSpanningTree(adjMatrix.length);
        t.computePrimMST(adjMatrix, features);
        e = t.getEdges();
        System.out.print(Arrays.deepToString(e));
    }
}
