package classificationmodel.bayesiannetwork.tree;

import classificationmodel.bayesiannetwork.graph.Node;
import classificationmodel.bayesiannetwork.graph.Counts;

import java.util.*;

public class MyTreeNode extends TreeNode {

    private Counts n;
    private Map<String, double[][][]> theta_ijkc;
    private double[] theta_c;

    // Constructor:

    /**
     * Initializes fields.
     *
     * @param n Node corresponding to the same feature as the present TreeNode
     */
    MyTreeNode(Counts n) {
        super();
        this.n = n;
    }

    /**
     * Returns the node.
     *
     * @return Node
     */
    public Counts getN() { return n; }

    /**
     * Returns theta_c counts.
     *
     * @return theta_c
     */
    public double[] getTheta_c() { return theta_c; }

    /**
     * Returns theta_ijkc counts associated to a given key.
     *
     * @param key   Key in the Map
     * @return      Data in the map corresponding to the key
     */
    public double[][][] getThetaijkc(String key){
        return this.theta_ijkc.get(key);
    }

    /**
     * Removes Nijkc counts from Node to all nodes that are not the parent.
     */
    void setNijkc() {
        Map<String, int[][][]> map = ((Node)this.n).getNijkcMap();
        List<String> not_parents = new LinkedList<String>();
        String _parent;

        if (this.getisRoot()) {
            _parent = this.getIdentifier();
        } else {
            _parent = this.getParent().getIdentifier();
        }
        for (String key : map.keySet()){
            if (!(_parent.equals(key)))  not_parents.add(key);
        }
        for (String not_parent : not_parents){
            map.remove(not_parent);
        }
    }

    /**
     * Removes NKijc counts from Node to all nodes that are not the parent.
     */
    void setNKijc() {
        Map<String, int[][]> map = ((Node)this.n).getNKijcMap();
        List<String> not_parents = new LinkedList<String>();
        String parent;

        if (this.getisRoot()) {
            parent = this.getIdentifier();
        } else {
            parent = this.getParent().getIdentifier();
        }
        for (String key : map.keySet()){
            if (!(parent.equals(key)))  not_parents.add(key);
        }
        for (String not_parent : not_parents){
            map.remove(not_parent);
        }
    }

    /**
     * Calculates theta_ijkc.
     */
    void setTheta_ijkc() {
        Map<String, double[][][]> map = new HashMap<>();

        for (String key : ((Node)this.n).getNijkcMap().keySet()) {
            int[][][] Nijkc = ((Node)this.n).getNijkc(key);
            int[][] NKijc = ((Node)this.n).getNKijc(key);
            double [][][] theta_ijkc = new double[Nijkc.length][Nijkc[0].length][Nijkc[0][0].length];

            for (int j=0; j < Nijkc.length; j++){
                for (int k=0; k < Nijkc[0].length ;k++){
                    for(int c=0; c < Nijkc[0][0].length; c++){
                        theta_ijkc[j][k][c] = (Nijkc[j][k][c] + 0.5)/(NKijc[j][c] + ((Node)this.n).getR() * 0.5);
                        map.put(key, theta_ijkc);
                    }
                }
            }
        }
        this.theta_ijkc = map;
    }

    /**
     * Calculates theta_c.
     */
    void setTheta_c() {
        double[] aux = new double[Node.getNc().length];
        for (int c = 0; c < Node.getNc().length; c++) {
            aux[c] = (Node.getNc()[c] + 0.5)/(Node.getN() + Node.getNc().length * 0.5);
        }
        this.theta_c = aux;
    }

    /**
     * Calculates theta counts and remove extra Nijkc and NKijc counts.
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
