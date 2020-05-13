package classificationmodel.bayesiannetwork.tree;

import java.util.List;

/**
 * Declares methods to implement a maximum spanning tree-
 */
public interface MaxSpanningTree {
    /**
     * Computes a maximum spanning tree and returns a directed tree. Defines the first node/feature as root.
     *
     * @return  The tree structure composed of a List of TreeNodes
     */
    List<TreeNode> computeMST();

    //TODO: check if it is ever used
    TreeNode getRoot();
}
