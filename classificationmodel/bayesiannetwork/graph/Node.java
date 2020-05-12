package classificationmodel.bayesiannetwork.graph;

import java.util.HashMap;
import java.util.Map;

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

    protected Node(String feature_name, int r, int id){
        this.feature_name = feature_name;
        this.r = r;
        this.nodeID = id;
    }

    // Static methods
    protected static void setNc(double[] nc) { Nc = nc; }
    protected static void setN(double n) { N = n; }
    protected static void setfeatures(String[] features) {Features = features; }
    protected static void setR_values(int[] r_values) {R_values = r_values; }
    protected static void setNum_class(int num_class) {Num_class = num_class; }

    // Static getters
    public static double[] getNc() {
        return Nc;
    }
    public static double getN() {
        return N;
    }
    protected static String[] getFeatures() { return Features; }
    protected static int[] getR_values() { return R_values; }
    protected static int getNum_class() { return Num_class; }
    protected String getFeature_name() {return feature_name;}
    public Map<String, int[][][]> getNijkcMap(){
        return this.Nijkc;
    }
    public Map<String, int[][]> getNKijcMap() { return this.NKijc; }
    public int[][][] getNijkc(String key){
        return this.Nijkc.get(key);
    }
    public int[][] getNJikc(String key){
        return this.NJikc.get(key);
    }
    public int[][] getNKijc(String key){
        return this.NKijc.get(key);
    }
    public int getQ(String key) {
        return q.get(key);
    }
    public int getR() {
        return r;
    }

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

    public void setR(int r) {
        this.r = r;
    }

    protected void inc_Nijkc(String key, int j, int k, int c){
        int[][][] aux = this.Nijkc.get(key);
        aux[j][k][c]++;
        this.Nijkc.put(key, aux);
    }

    protected void inc_NKijc(String key, int j, int c) {
        int[][] aux = this.NKijc.get(key);
        aux[j][c]++;
        this.NKijc.put(key, aux);
    }

    protected void inc_NJikc(String key, int k, int c) {
        int[][] aux = this.NJikc.get(key);
        aux[k][c]++;
        this.NJikc.put(key, aux);
    }

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

    protected void print_NKijc(String key) {
        int[][] aux = this.NKijc.get(key);
        for (int c = 0; c < aux[0].length; c++) {
            for (int j = 0; j < aux.length; j++) {
                System.out.print(aux[j][c] + " ");
            }
            System.out.println();
        }
    }

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