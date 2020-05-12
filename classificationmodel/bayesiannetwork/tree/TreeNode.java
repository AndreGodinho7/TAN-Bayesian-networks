package classificationmodel.bayesiannetwork.tree;

import java.util.ArrayList;
import java.util.List;

public abstract class TreeNode {
    private List<TreeNode> children;
    private TreeNode parent;
    private boolean isRoot;
    private String identifier;

    public TreeNode() {
        this.children = new ArrayList<TreeNode>();
        this.isRoot = false;
    }

    // getters
    public String getIdentifier() { return this.identifier; }
    public TreeNode getParent() { return parent; }
    public boolean getisRoot() { return isRoot; }
    public List<TreeNode> getChildren() { return children; }

    // setters
    public abstract void setData();
    protected void setAsRoot() {  this.isRoot = true; }
    protected void setIdentifier(String i) { this.identifier = i; }
    protected void setParent(TreeNode p) { this.parent = p; }
    protected void addChild(TreeNode child) { this.children.add(child); }

    //TODO: remove print methods
    public void printChildren() {
        List<TreeNode> tns = this.children;
        System.out.println("Father: " + this.getIdentifier());
        System.out.print("Children: ");
        for (TreeNode tn : tns) { System.out.print(tn.getIdentifier() + " "); }
        System.out.println();
    }
}

