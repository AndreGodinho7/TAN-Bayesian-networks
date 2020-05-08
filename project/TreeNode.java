package project;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class TreeNode {
    protected List<TreeNode> children;
    protected TreeNode parent;
    private String identifier;

    TreeNode() {
        this.children = new ArrayList<TreeNode>();
    }
    public void setIdentifier(String i) { this.identifier = i; }

    public String getIdentifier() { return this.identifier; }

    public void addChild(TreeNode child) { this.children.add(child); }

    public void setParent(TreeNode p) { this.parent = p; }

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

