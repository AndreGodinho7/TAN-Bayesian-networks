package project;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyTreeNode extends TreeNode {

    private Node n;
    private Map<String, double[][][]> theta_ijkc;
    private double[] theta_c;

    // Constructor:
    public MyTreeNode(Node n) {
        super();
        this.n = n;
    }

    // Getter methods:
    public String getTreeNodeName() { return this.n.getFeature_name(); }

    /**
     * Remove Nijkc counts from Node to all nodes that are not children.
     */
    public void setNijkc() {
        Map<String, int[][][]> map = this.n.getNijkcMap();
        boolean isChild = false;

        for (String key : map.keySet()) {
            for (TreeNode child : this.children) {
                if (child.getIdentifier().equals(key)) {
                    isChild = true;
                    break;
                }
            }
            if (!isChild) {this.n.removeKeyNijkc(key);}
        }
    }

    /**
     * Remove NKijc counts from Node to all nodes that are not children.
     */
    public void setNKijc() {
        Map<String, int[][]> map = this.n.getNKijcMap();
        boolean isChild = false;

        for (String key : map.keySet()) {
            for (TreeNode child : this.children) {
                if (child.getIdentifier().equals(key)) {
                    isChild = true;
                    break;
                }
            }
            if (!isChild) {this.n.removeKeyNKijc(key);}
        }
    }

    /**
     * Calculate theta_ijkc
     */
    public void setTheta_ijkc() {
        Map<String, double[][][]> map = new HashMap<String, double[][][]>();

        for (String key : this.n.getNijkcMap().keySet()) {
            double[][][] aux = map.get(key);
            int[][][] nijkc = this.n.getNijkcMap().get(key);
            int[][] nkijc = this.n.getNKijcMap().get(key);

            for (int j=0; j < nijkc.length; j++){
                for (int k=0; k < nijkc[0].length ;k++){
                    for(int c=0; c < nijkc[0][0].length; c++){
                        aux[j][k][c] = (nijkc[j][k][c] + 0.5)/(nkijc[j][c] + this.n.getR() * 0.5);
                        map.put(key, aux);
                    }
                }
            }
        }
        this.theta_ijkc = map;
    }

    /**
     * Calculate theta_c
     */
    public void setTheta_c() {
        double[] aux = new double[Node.getNc().length];
        for (int c = 0; c < Node.getNc().length; c++) {
            aux[c] = (Node.getNc()[c] + 0.5)/(Node.getN() + Node.getNc().length * 0.5);
        }
        this.theta_c = aux;
    }

    /**
     * Calculate theta counts and remove extra Nijkc and NKijc counts
     */
    public void setData() {
        setNijkc();
        setNKijc();
        setTheta_ijkc();
        setTheta_ijkc();
    }

    // TODO: remove print methods
    public void printTheta_ijkc(String key) {
        double[][][] aux = this.theta_ijkc.get(key);
        for (int c = 0; c < aux[0][0].length; c++) {
            for (int k = 0; k < aux.length; k++) {
                for (int j = 0; j < aux[0].length; j++) {
                    System.out.print(aux[j][k][c] + " ");
                }
                System.out.print("  ");
            }
            System.out.println();
        }
    }

}
