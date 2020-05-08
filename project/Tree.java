package project;

import java.util.ArrayList;
import java.util.List;

public class Tree {
    private TreeNode root;

    public static class Node<T> {
        private T data;
        private Node parent;
        private List<Node> children;

        Node(T d, Node<T> p) {
            this.data = d;
            this.parent = p;
            this.children = new ArrayList<Node<T>>();
        }
        // getter methods:
        //public T getData() {return this.data;}
        //public Node<T> getParent() {return this.parent;}

        // setter methods:
        //public void setData(T newData) {this.data = newData;}
        //public void setParent(Node<T> _root) {this.parent = _root;}

        //public void addChild(Node<T> _child) {this.children.add(_child);}
    }

    public Tree(T rootData) {
        this.root = new Node(rootData, null);
        //this.root.data = rootData;
        //this.root.children = new ArrayList<Node<T>>();
    }

    public void addChild(T childData) {
        Node newChild = new Node(childData, this.root);
        this.root.children.add(newChild);
    }
}
