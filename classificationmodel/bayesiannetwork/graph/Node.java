package classificationmodel.bayesiannetwork.graph;

import java.util.HashMap;
import java.util.Map;

/**
 * Implements methods to define and store data of a node. The counts Nijkc, NJikc, NKijc associated to edges from the
 * the node to each one of its parents are stored in HashMaps. In the case of a complete graph all the remaining nodes
 * are parents of the present node, which means the HashMap will store counts regarding all the connections to these
 * nodes.
 */
public class Node implements Counts {
    private static double[] Nc;
    private static double N;
    private static String[] Features;
    private static int[] R_values;
    private static int Num_class;

    private String feature_name;
    private int r;
    private int nodeID;

    private Map<String, int[][][]> Nijkc;
    private Map<String, int[][]> NKijc;
    private Map<String, int[][]> NJikc;
    private Map<String, Integer> q;

    /**
     * Initializes the following class fields: feature_name, r, and nodeID.
     *
     * @param feature_name  Feature name
     * @param r             Number of values the corresponding feature may take
     * @param id            Feature index in the array of strings storing the feature names
     */
    protected Node(String feature_name, int r, int id){
        this.feature_name = feature_name;
        this.r = r;
        this.nodeID = id;
    }

    // Static methods

    /**
     * Sets the number of occurrences for each class.
     *
     * @param nc    Array storing the number of occurrences of each class
     */
    protected static void setNc(double[] nc) { Nc = nc; }

    /**
     * Sets the number of instances.
     *
     * @param n     Number of instances
     */
    protected static void setN(double n) { N = n; }

    /**
     * Sets the feature names.
     *
     * @param features  Array of strings storing the feature names
     */
    protected static void setfeatures(String[] features) {Features = features; }

    /**
     * Sets the number of different values each feature may take.
     *
     * @param r_values  Array of integers storing the number of values each feature may take
     */
    protected static void setR_values(int[] r_values) {R_values = r_values; }

    /**
     * Sets the total number of classes.
     *
     * @param num_class     Total number of classes
     */
    protected static void setNum_class(int num_class) {Num_class = num_class; }

    // Static getters

    /**
     * Returns an array storing the number of occurrences of each class.
     *
     * @return  Array storing the number of occurrences of each class
     */
    public static double[] getNc() {
        return Nc;
    }

    /**
     * Returns the total number of instances.
     *
     * @return      Total number of instances
     */
    public static double getN() {
        return N;
    }

    /**
     * Returns an array of strings storing the names of features.
     *
     * @return  Array of strings storing the names of features
     */
    protected static String[] getFeatures() { return Features; }

    /**
     * Returns an array of integers storing the number of values each feature may take.
     *
     * @return  Array of integers storing the number of values each feature may take
     */
    protected static int[] getR_values() { return R_values; }

    /**
     * Returns the total number of classes.
     *
     * @return  Total number of classes
     */
    protected static int getNum_class() { return Num_class; }

    /**
     * Returns the feature name.
     *
     * @return  Feature name
     */
    protected String getFeature_name() {return feature_name;}

    /**
     * Returns the Map storing Nijkc counts.
     *
     * @return  Map storing Nijkc counts
     */
    public Map<String, int[][][]> getNijkcMap(){
        return this.Nijkc;
    }

    /**
     * Returns the Map storing NKijc counts.
     *
     * @return  Map storing NKijc counts
     */
    public Map<String, int[][]> getNKijcMap() { return this.NKijc; }

    /**
     * Returns the data stored for a given key of the Nijkc counts Map.
     * @param key   Key in the Map
     * @return      Data associated to the key in the Map
     */
    public int[][][] getNijkc(String key){
        return this.Nijkc.get(key);
    }

    /**
     * Returns the data stored for a given key of the NJikc counts Map.
     * @param key   Key in the Map
     * @return      Data associated to the key in the Map
     */
    public int[][] getNJikc(String key){
        return this.NJikc.get(key);
    }

    /**
     * Returns only the data stored for a given key of the NKijc counts Map.
     * @param key   Key in the Map
     * @return      Data associated to the key in the Map
     */
    public int[][] getNKijc(String key){
        return this.NKijc.get(key);
    }

    /**
     * Returns number of possible parent configurations for a parent specified by the key.
     *
     * @param key   Key specifying the parent
     * @return      Number of possible parent configurations
     */
    public int getQ(String key) {
        return q.get(key);
    }

    /**
     * Returns the number of different values the feature may take.
     *
     * @return  Number of different values the feature may take
     */
    public int getR() {
        return r;
    }

    /**
     * Sets Nijkc counts Map.
     *
     * @param keys      Key in the Map corresponding to a connection to the node itself
     * @param features  Array of strings storing feature names
     * @param r_values  Array of integers storing the number of different values each feature may take
     * @param classes   The class for which the count occurred
     */
    protected void setNijkc(int keys, String[] features, int[]r_values, int classes){
        Map<String, int[][][]> map = new HashMap<String, int[][][]>();

        for (int i=0; i < features.length; i++){
            if (i == keys) {
                map.put(features[keys], new int[1][this.r][classes]);
            }
            else{
                map.put(features[i], new int[r_values[i]][this.r][classes]);
            }
        }

        System.out.println("Hashmap of feature:"+features[keys]);
        map.entrySet().forEach(entry->{
            System.out.println("key:"+entry.getKey() + " value:" + "int["+entry.getValue().length+"]"+"["+entry.getValue()[0].length+"]"+"["+entry.getValue()[0][0].length+"]");
        });
        System.out.println();
        this.Nijkc = map;
    }

    /**
     * Sets NKijc counts Map.
     *
     * @param keys      Key in the Map corresponding to a connection to the node itself
     * @param features  Array of strings storing feature names
     * @param r_values  Array of integers storing the number of different values each feature may take
     * @param classes   The class for which the count occurred
     */
    protected void setNKijc(int keys, String[] features, int[]r_values, int classes){
        Map<String, int[][]> map = new HashMap<String, int[][]>();

        for (int i=0; i < features.length; i++){
            if (i == keys) {
                map.put(features[keys], new int[1][classes]);
            }
            else{
                map.put(features[i], new int[r_values[i]][classes]);
            }
        }

        System.out.println("NKijc Hashmap of feature "+features[keys]);
        map.entrySet().forEach(entry->{System.out.println("key:" + entry.getKey() + " value:"
                + "int["+ entry.getValue().length + "]"
                + "[" + entry.getValue()[0].length + "]");});
        this.NKijc = map;
    }

    /**
     * Sets NJikc counts Map.
     *
     * @param keys      Key in the Map corresponding to a connection to the node itself
     * @param features  Array of strings storing feature names
     * @param r_values  Array of integers storing the number of different values each feature may take
     * @param classes   The class for which the count occurred
     */
    protected void setNJikc(int keys, String[] features, int[]r_values, int classes){
        Map<String, int[][]> map = new HashMap<String, int[][]>();

        for (int i=0; i < features.length; i++){
            if (i == keys) {
                map.put(features[keys], new int[this.r][classes]);
            }
            else{
                map.put(features[i], new int[this.r][classes]);
            }
        }

        System.out.println("NJikc Hashmap of feature "+features[keys]);
        map.entrySet().forEach(entry->{System.out.println("key:" + entry.getKey() + " value:"
                + "int[" + entry.getValue().length + "]"
                + "[" + entry.getValue()[0].length + "]");});
        this.NJikc = map;
    }

    /**
     * Sets number of parent configurations.
     *
     * @param keys      Key in the Map corresponding to a connection to the node itself
     * @param features  Array of strings storing feature names
     * @param r         Number of different values the feature may take
     */
    protected void setQ(int keys, String[] features, int r){
        Map<String, Integer> map = new HashMap<String, Integer>();
        for (int i=0; i < features.length; i++){
            if (i == keys) {
                map.put(features[keys], 1); // empty configuration
            }
            else{
                map.put(features[i], r);
            }
        }

        System.out.println("q Hashmap of feature "+features[keys]);
        map.entrySet().forEach(entry->{System.out.println("key:" + entry.getKey() + " value:"
                +entry.getValue());});
        this.q = map;
    }

    /**
     * Sets the number of different values the feature may take.
     * @param r      Number of different values the feature may take
     */
    public void setR(int r) {
        this.r = r;
    }

    /**
     * Increments the Nijkc counts after processing a new instance.
     *
     * @param key   Key of the hashmap whose data will be incremented
     * @param j     Parent configuration j
     * @param k     Child configuration k
     * @param c     Class c
     */
    protected void inc_Nijkc(String key, int j, int k, int c){
        int[][][] aux = this.Nijkc.get(key);
        aux[j][k][c]++;
        this.Nijkc.put(key, aux);
    }

    /**
     * Increments the NKijc counts after processing a new instance.
     *
     * @param key   Key of the hashmap whose data will be incremented
     * @param j     Parent configuration j
     * @param c     Class c
     */
    protected void inc_NKijc(String key, int j, int c) {
        int[][] aux = this.NKijc.get(key);
        aux[j][c]++;
        this.NKijc.put(key, aux);
    }

    /**
     * Increments the NJikc counts after processing a new instance.
     *
     * @param key   Key of the hashmap whose data will be incremented
     * @param k     Child configuration k
     * @param c     Class c
     */
    protected void inc_NJikc(String key, int k, int c) {
        int[][] aux = this.NJikc.get(key);
        aux[k][c]++;
        this.NJikc.put(key, aux);
    }

    //TODO: remove print method
    protected void print_Nijkc(String key) {
        int[][][] aux = this.Nijkc.get(key);
        for (int z = 0; z < aux[0][0].length; z++) {
            for (int x = 0; x < aux.length; x++) {
                for (int y = 0; y < aux[0].length; y++) {
                    System.out.print(aux[x][y][z] + " ");
                }
                System.out.print("  ");
            }
            System.out.println();
        }
    }
    //TODO: remove print method
    protected void print_NKijc(String key) {
        int[][] aux = this.NKijc.get(key);
        for (int c = 0; c < aux[0].length; c++) {
            for (int j = 0; j < aux.length; j++) {
                System.out.print(aux[j][c] + " ");
            }
            System.out.println();
        }
    }
    //TODO: remove print method
    protected void print_NJikc(String key) {
        int[][] aux = this.NJikc.get(key);
        for (int c = 0; c < aux[0].length; c++) {
            for (int k = 0; k < aux.length; k++) {
                System.out.print(aux[k][c] + " ");
            }
            System.out.println();
        }
    }

    @Override
    public void setCounts() {
        this.setNijkc(this.nodeID, Features, R_values, Num_class);
        this.setNJikc(this.nodeID, Features, R_values, Num_class);
        this.setNKijc(this.nodeID, Features, R_values, Num_class);
        this.setQ(this.nodeID, Features, R_values[this.nodeID]);
        this.setR(R_values[this.nodeID]);
    }

    /**
     * Increment all counts (Nijkc, NKijc, NJikc) for the present node.
     *
     * @param sample    New instance (array of integer values for each feature)
     */
    @Override
    public void incrementCounts(int[] sample) {
        String[] features = Features;
        int aux_class;
        Map<String, Integer> aux_map = new HashMap<String, Integer>();
        for (int i = 0; i < sample.length - 1; i++) {
            aux_map.put(features[i], sample[i]);
        }
        aux_class = sample[sample.length - 1];

        for (String key : this.Nijkc.keySet()) {
            if (this.getFeature_name().equals(key)) {
                this.inc_Nijkc(key, 0, aux_map.get(this.getFeature_name()), aux_class);
                this.inc_NKijc(key,0, aux_class);
                this.inc_NJikc(key, aux_map.get(this.getFeature_name()), aux_class);
            } else {
                this.inc_Nijkc(key, aux_map.get(key), aux_map.get(this.getFeature_name()), aux_class);
                this.inc_NKijc(key, aux_map.get(key), aux_class);
                this.inc_NJikc(key, aux_map.get(this.getFeature_name()), aux_class);
            }
        }
    }
}