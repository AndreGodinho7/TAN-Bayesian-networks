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
    private int rootIndex = 0;

    // Constructor
    public PrimAlgorithm(MyGraph _graph) {
        this.graph = _graph;
        this.treeNodes = new ArrayList<TreeNode>(_graph.getFile().getFeatures().length);
    }

    //Getter methods:
    public List<TreeNode> getTreeNodesList() { return this.treeNodes; }

    /* decide where to put this code (previously inside constructor):

    // Setting first node as root of the tree
    this.isRoot = new boolean[this.graph.numNodes()];
    Arrays.fill(isRoot, false);
    isRoot[0] = true;
    //create first MyTreeNode and add it to the directed tree
    root = new MyTreeNode(this.graph.getNodes().get(0));
    directedTree = new Tree<MyTreeNode>(root);
     */

    /**
     * Verify if first node has any edges ( edge's weight > 0). If yes, define it as root, if not, repeat the process
     * for the second node, and so on.
     */
    public void defineRoot() {
        boolean notConnected = true;
        // The graph is assumed to be undirected -- symmetric adjacency matrix
        for (int line = 0; line < this.graph.numNodes() - 1; line++) {
            for (int col = line + 1; col < this.graph.numNodes(); col++) {
                if (this.graph.getAdjMatrix()[line][col] != 0) {
                    notConnected = false;
                    break;
                }
            }
            if (!notConnected) {
                break;
            } else {
                this.rootIndex++;
                if (this.rootIndex == this.graph.numNodes() - 1) {
                    System.out.println("The graph does not have any edges!");
                    System.exit(1);
                }
            }
        }
        System.out.println("\nRoot Node: " + graph.getFile().getFeatures()[this.rootIndex]);
    }

    /**
     * Create each tree node inside the array list of TreeNodes
     */
    private void setTreeNodes() {
        for (int i = 0; i < this.graph.numNodes(); i++) {
            TreeNode tn = new MyTreeNode(this.graph.getNodes().get(i));
            tn.setIdentifier(this.graph.getFile().getFeatures()[i]);
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

        for (int node = this.rootIndex; node < this.graph.numNodes(); node++)
            if (!mstSet[node] && (key[node] > max)) {
                max = key[node];
                max_index = node;
            }
        return max_index;
    }

    private Tree createTree(List<MyTreeNode> _treeNodes){


    }

    /**
     * Compute maximum spanning tree from the graph's adjacency matrix
     */
    @Override
    public void computeMST() {

        int[] parent = new int[this.graph.numNodes()];         // Nodes added to MST
        double[] key = new double[this.graph.numNodes()];        // Key values used to pick max weight edge
        Boolean[] mstSet = new Boolean[this.graph.numNodes()];

        defineRoot();
        setTreeNodes();

        for (int i = 0; i < this.graph.numNodes(); i++) {
            key[i] = -1;
            mstSet[i] = false;
        }
        key[this.rootIndex] = Double.MAX_VALUE; // Make key MAX so that this node is picked as first node
        parent[this.rootIndex] = -1;            // Root node does not have a parent

        for (int count = this.rootIndex; count < this.graph.numNodes() - 1; count++) {
            int u = maxKey(key, mstSet);
            mstSet[u] = true;

            for (int v = this.rootIndex; v < this.graph.numNodes(); v++) {
                if ((this.graph.getAdjMatrix()[u][v] != 0) && !mstSet[v] && (this.graph.getAdjMatrix()[u][v] > key[v])) {
                    parent[v] = u;
                    key[v] = this.graph.getAdjMatrix()[u][v];
                }
            }
        }
        /* este ciclo guarda os filhos de cada nó no nó respectivo. Não precisa de percorrer a raíz porque esta não tem
           pai */
        for (int i = this.rootIndex + 1; i < this.graph.numNodes(); i++) {
            if (parent[i] != -1 && this.graph.getAdjMatrix()[i][parent[i]] > 0) {
                this.treeNodes.get(parent[i]).addChild(this.treeNodes.get(i));
                this.treeNodes.get(i).setParent(this.treeNodes.get(parent[i]));
            }
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

    // TODO: remove this method after testing
    void printMST(int[] parent) {

        System.out.println("Edge \tWeight");
        for (int i = this.rootIndex + 1; i < this.graph.numNodes(); i++) {
            if (parent[i] != -1 && this.graph.getAdjMatrix()[i][parent[i]] > 0) {
                System.out.println(parent[i] + 1 + " - " + (i + 1) + "\t" + this.graph.getAdjMatrix()[i][parent[i]]);
            }
        }

    }
}
