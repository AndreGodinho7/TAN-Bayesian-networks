package project;


import java.lang.*;
import java.util.ArrayList;
import java.util.List;

public class PrimAlgorithm  implements MaxSpanningTree{

    TreeNode root;
    private List<TreeNode> treeNodes;

    private MyGraph graph;
    private int rootIndex = 0;

    // Constructor
    public PrimAlgorithm(MyGraph _graph) {
        this.graph = _graph;
        this.treeNodes = new ArrayList<TreeNode>(_graph.getFile().getFeatures().length);
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
        double max = -Double.MAX_VALUE;
        int max_index = -1;

        for (int node = this.rootIndex; node < this.graph.numNodes(); node++)
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
    public List<TreeNode> computeMST() {

        int[] parent = new int[this.graph.numNodes()];         // Nodes added to MST
        double[] key = new double[this.graph.numNodes()];        // Key values used to pick max weight edge
        Boolean[] mstSet = new Boolean[this.graph.numNodes()];

        setTreeNodes();
        //defineRoot();
        this.treeNodes.get(rootIndex).setAsRoot();

        for (int i = 0; i < this.graph.numNodes(); i++) {
            key[i] = -Double.MAX_VALUE + Double.MIN_VALUE;//-1;
            mstSet[i] = false;
        }
        key[this.rootIndex] = Double.MAX_VALUE; // Make key MAX so that this node is picked as first node
        parent[this.rootIndex] = -1;            // Root node does not have a parent

        for (int count = this.rootIndex; count < this.graph.numNodes() - 1; count++) {
            int u = maxKey(key, mstSet);
            mstSet[u] = true;

            for (int v = this.rootIndex; v < this.graph.numNodes(); v++) {
                //if ((this.graph.getAdjMatrix()[u][v] != 0) && !mstSet[v] && (this.graph.getAdjMatrix()[u][v] > key[v])) {
                if ((u != v) && !mstSet[v] && (this.graph.getAdjMatrix()[u][v] > key[v])) {
                    parent[v] = u;
                    key[v] = this.graph.getAdjMatrix()[u][v];
                }
            }
        }
        /* este ciclo guarda os filhos de cada nó no nó respectivo. Não precisa de percorrer a raíz porque esta não tem
           pai */
        for (int i = this.rootIndex + 1; i < this.graph.numNodes(); i++) {
            if (parent[i] != -1) {
                this.treeNodes.get(parent[i]).addChild(this.treeNodes.get(i));
                this.treeNodes.get(i).setParent(this.treeNodes.get(parent[i]));
            }
        }

        //-------------------------------------------------------------------------------------------------
        // TODO: remove print methods afterwards
        printMST(parent);

        // Print children of each node after running Prim algorithm
        List<TreeNode> aux = this.treeNodes;
        for (TreeNode tna : aux) {
            tna.printChildren();
            System.out.println();
        }

        return this.treeNodes;
    }

    @Override
    public TreeNode getRoot() {
        return this.root;
    }


    // TODO: remove this method after testing
    void printMST(int[] parent) {

        System.out.println("Edge \tWeight");
        for (int i = this.rootIndex + 1; i < this.graph.numNodes(); i++) {
            if (parent[i] != -1) {
                System.out.println(parent[i] + 1 + " - " + (i + 1) + "\t" + this.graph.getAdjMatrix()[i][parent[i]]);
            }
        }

    }
}
