package project;

import com.sun.source.tree.Tree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyTreeNode implements TreeNode {

    private Node n;

    /*private String feature_name;
    private int r;
    private double [] Nc;
    private double N;
    private final float N_prime = 0.5f;
    private Map<String, int[][][]> Nijkc;
    private Map<String, int[][]> NKijc;*/

    private Map<String, double[][][]> theta_ijkc;
    private double[] theta_c;
    private List<String> children;

    // Constructor:
    public MyTreeNode(Node _n) {
        this.n = _n;
        this.children = new ArrayList<String>();
    }

    /*public TreeNode(String _feature_name, int _r) {
        this.feature_name = _feature_name;
        this.r = _r;
        this.Nc = Node.getNc();
        this.N = Node.getN();
    }*/
    public void addChildName(String nodeName) { this.children.add(nodeName); }


    /**
     * Get Nijkc from Node and remove all extra edges.
     * @param children : get child Node to remove all edges except edge to child
     */
    @Override
    public void setNijkc(String[] children) {
        Map<String, int[][][]> map = new HashMap<String, int[][][]>();
        map = this.n.getNijkcMap();
        boolean isChild = false;

        for (String key : map.keySet()) {
            for (String child : children) {
                if (child.equals(key)) {
                    isChild = true;
                    break;
                }
            }
            if (!isChild) {this.n.removeKeyNijkc(key);}
        }
    }

    /**
     * Get NKijc from Node and remove all extra edges.
     * @param children : get child Node to remove all edges except edge to child
     */
    @Override
    public void setNKijc(String[] children) {
        Map<String, int[][]> map = new HashMap<String, int[][]>();
        map = this.n.getNKijcMap();
        boolean isChild = false;

        for (String key : map.keySet()) {
            for (String child : children) {
                if (child.equals(key)) {
                    isChild = true;
                    break;
                }
            }
            if (!isChild) {this.n.removeKeyNKijc(key);}
        }
    }

    @Override
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
    @Override
    public void setTheta_c() {
        double[] aux = new double[Node.getNc().length];
        for (int c = 0; c < Node.getNc().length; c++) {
            aux[c] = (Node.getNc()[c] + 0.5)/(Node.getN() + Node.getNc().length * 0.5);
        }
        this.theta_c = aux;
    }

    //--------------------------------------------------------------------
    // print methods:
    public void printChildren() {
        List<String> ns = this.children;
        System.out.println("Father: " + this.n.getFeature_name());
        for (String name : ns) { System.out.print(name + " "); }
    }

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
