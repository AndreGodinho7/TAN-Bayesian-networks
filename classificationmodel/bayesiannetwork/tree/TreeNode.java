package classificationmodel.bayesiannetwork.tree;

import java.util.ArrayList;
import java.util.List;

/**
 * Implements the methods necessary to define and manipulate nodes in the tree (TreeNodes).
 */
public abstract class TreeNode {
    List<TreeNode> children;
    TreeNode parent;
    boolean isRoot;
    String identifier;

    /**
     * Initializes fields. Nodes are set as not being the root by default. The root is set inside the PrimAlgorithm
     * method.
     */
    public TreeNode() {
        this.children = new ArrayList<TreeNode>();
        this.isRoot = false;
    }

    // getters

    /**
     * Returns the name of the feature represented by the TreeNode
     *
     * @return  Name of the feature
     */
    public String getIdentifier() { return this.identifier; }

    /**
     * Returns the parent (of type TreeNode) of the present node.
     *
     * @return  Parent
     */
    public TreeNode getParent() { return parent; }

    /**
     * Returns true in case the present node is the root, if not, returns false.
     * @return  True or false
     */
    public boolean getisRoot() { return isRoot; }

    /**
     * Returns the List o children of the present node.
     *
     * @return  List of children
     */
    public List<TreeNode> getChildren() { return children; }

    // setters

    /**
     * Sets the counts in the TreeNode.
     */
    public abstract void setData();

    /**
     * Sets the present node as root.
     */
    void setAsRoot() {  this.isRoot = true; }

    /**
     * Sets the identifier of the TreeNode given the name of the feature.
     *
     * @param i     Name of the feature
     */
    void setIdentifier(String i) { this.identifier = i; }

    /**
     * Sets p as the parent of the present TreeNode.
     *
     * @param p     Parent
     */
    void setParent(TreeNode p) { this.parent = p; }

    /**
     * Adds child to the List of children of the present TreeNode.
     *
     * @param child     Child to be added to the list
     */
    void addChild(TreeNode child) { this.children.add(child); }

    //TODO: remove print methods
    public void printChildren() {
        List<TreeNode> tns = this.children;
        System.out.println("Father: " + this.getIdentifier());
        System.out.print("Children: ");
        for (TreeNode tn : tns) { System.out.print(tn.getIdentifier() + " "); }
        System.out.println();
    }
}

