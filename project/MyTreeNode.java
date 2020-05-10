package project;

import java.util.*;

public class MyTreeNode extends TreeNode {

    private Node n;
    private Map<String, double[][][]> theta_ijkc;
    private double[] theta_c;

    // Constructor:
    public MyTreeNode(Node n) {
        super();
        this.n = n;
    }

    public Node getN() { return n; }

    public double[] getTheta_c() { return theta_c; }

    public double[][][] getThetaijkc(String key){
        return this.theta_ijkc.get(key);
    }

    /**
     * Remove Nijkc counts from Node to all nodes that are not children.
     */
    public void setNijkc() {
        Map<String, int[][][]> map = this.n.getNijkcMap();
        List<String> not_parents = new LinkedList<String>();
        String _parent;

        if (this.isRoot) {
            _parent = this.getIdentifier();
        } else {
            _parent = this.parent.getIdentifier();
        }
        for (String key : map.keySet()){
            if (!(_parent.equals(key)))  not_parents.add(key);
        }
        for (String not_parent : not_parents){
            map.remove(not_parent);
        }
    }

    /**
     * Remove NKijc counts from Node to all nodes that are not children.
     */
    public void setNKijc() {
        Map<String, int[][]> map = this.n.getNKijcMap();
        List<String> not_parents = new LinkedList<String>();
        String parent;

        if (this.isRoot) {
            parent = this.getIdentifier();
        } else {
            parent = this.parent.getIdentifier();
        }
        for (String key : map.keySet()){
            if (!(parent.equals(key)))  not_parents.add(key);
        }
        for (String not_parent : not_parents){
            map.remove(not_parent);
        }
    }

    /**
     * Calculate theta_ijkc
     */
    public void setTheta_ijkc() {
        Map<String, double[][][]> map = new HashMap<>();

        for (String key : this.n.getNijkcMap().keySet()) {
            int[][][] Nijkc = this.n.getNijkc(key);
            int[][] NKijc = this.n.getNKijc(key);
            double [][][] theta_ijkc = new double[Nijkc.length][Nijkc[0].length][Nijkc[0][0].length];

            for (int j=0; j < Nijkc.length; j++){
                for (int k=0; k < Nijkc[0].length ;k++){
                    for(int c=0; c < Nijkc[0][0].length; c++){
                        theta_ijkc[j][k][c] = (Nijkc[j][k][c] + 0.5)/(NKijc[j][c] + this.n.getR() * 0.5);
                        map.put(key, theta_ijkc);
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
    @Override
    public void setData() {
        setNijkc();
        setNKijc();
        setTheta_ijkc();
        setTheta_c();
    }

    // TODO: remove print methods
    public void printTheta_ijkc(String key) {
        double[][][] aux = this.theta_ijkc.get(key);
        for (int c = 0; c < aux[0][0].length; c++) {
            for (int j = 0; j < aux.length; j++) {
                for (int k = 0; k < aux[0].length; k++) {
                    System.out.print(aux[j][k][c] + " ");
                }
                System.out.println();
            }
            System.out.println();
        }
    }

}
