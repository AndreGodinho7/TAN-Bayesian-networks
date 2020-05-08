package project;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class TreeNode {
    protected List<TreeNode> children;
    protected TreeNode parent;
    protected boolean isRoot;
    private String identifier;

    TreeNode() {
        this.children = new ArrayList<TreeNode>();
        this.isRoot = false;
    }
    public void setAsRoot() {  this.isRoot = true; }

    public void setIdentifier(String i) { this.identifier = i; }

    public String getIdentifier() { return this.identifier; }

    public TreeNode getParent() { return parent; }

    public void addChild(TreeNode child) { this.children.add(child); }

    public void setParent(TreeNode p) { this.parent = p; }

    public List<TreeNode> getChildren() { return children; }

    public abstract void setData();

    //TODO: remove print methods
    public void printChildren() {
        List<TreeNode> tns = this.children;
        System.out.println("Father: " + this.getIdentifier());
        System.out.print("Children: ");
        for (TreeNode tn : tns) { System.out.print(tn.getIdentifier() + " "); }
        System.out.println();
    }
}

