package project;

import java.util.HashMap;
import java.util.Map;

public class Node implements Counter{
    private static double[] Nc;
    private static double N;

    private String feature_name;
    private Map<String, int[][][]> Nijkc;
    private Map<String, int[][]> NKijc;
    private Map<String, int[][]> NJikc;
    private Map<String, Integer> q;
    private int r;

    public Node(String feature_name, int r) {
        this.feature_name = feature_name;
        this.r = r;
    }

    @Override
    public void setNijkc(int keys, String[] features, int[]r_values, int classes){
        Map<String, int[][][]> map = new HashMap<String, int[][][]>();

        for (int i=0; i < features.length; i++){
            if (i == keys) {
                map.put(features[keys], new int[1][r_values[keys]][classes]);
            }
            else{
                map.put(features[i], new int[r_values[keys]][r_values[i]][classes]);
            }
        }

        System.out.println("Hashmap of feature:"+features[keys]);
        map.entrySet().forEach(entry->{
            System.out.println("key:"+entry.getKey() + " value:" + "int["+entry.getValue().length+"]"+"["+entry.getValue()[0].length+"]"+"["+entry.getValue()[0][0].length+"]");
        });
        System.out.println();
        this.Nijkc = map;
    }
    @Override
    public void setNKijc(int keys, String[] features, int[]r_values, int classes){
        Map<String, int[][]> map = new HashMap<String, int[][]>();

        for (int i=0; i < features.length; i++){
            if (i == keys) {
                map.put(features[keys], new int[1][classes]);
            }
            else{
                map.put(features[i], new int[r_values[keys]][classes]);
            }
        }



        System.out.println("NKijc Hashmap of feature "+features[keys]);
        map.entrySet().forEach(entry->{System.out.println("key:" + entry.getKey() + " value:"
                + "int["+ entry.getValue().length + "]"
                + "[" + entry.getValue()[0].length + "]");});
        this.NKijc = map;
    }

    @Override
    public void setNJikc(int keys, String[] features, int[]r_values, int classes){
        Map<String, int[][]> map = new HashMap<String, int[][]>();

        for (int i=0; i < features.length; i++){
            if (i == keys) {
                map.put(features[keys], new int[r_values[keys]][classes]);
            }
            else{
                map.put(features[i], new int[r_values[i]][classes]);
            }
        }

        System.out.println("NJikc Hashmap of feature "+features[keys]);
        map.entrySet().forEach(entry->{System.out.println("key:" + entry.getKey() + " value:"
                + "int[" + entry.getValue().length + "]"
                + "[" + entry.getValue()[0].length + "]");});
        this.NJikc = map;
    }

    @Override
    public void setQ(int keys, String[] features, int r){
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

    @Override
    public void setR(int r) {
        this.r = r;
    }

    public static void setNc(double[] nc) { Nc = nc; }

    public static void setN(double n) { N = n; }

    // Getter methods:
    @Override
    public String getFeature_name() {return feature_name;}
    @Override
    public Map<String, int[][][]> getNijkcMap(){
        return this.Nijkc;
    }
    @Override
    public int[][][] getNijkc(String key){
        return this.Nijkc.get(key);
    }
    @Override
    public int[][] getNJikc(String key){
        return this.NJikc.get(key);
    }
    @Override
    public int[][] getNKijc(String key){
        return this.NKijc.get(key);
    }
    @Override
    public int getQ(String key) {
        return q.get(key);
    }

    public static double[] getNc() {
        return Nc;
    }

    public static double getN() {
        return N;
    }
    @Override
    public int getR() {
        return r;
    }
    @Override
    public void inc_Nijkc(String key, int j, int k, int c){
        int[][][] aux = this.Nijkc.get(key);
        aux[j][k][c]++;
        this.Nijkc.put(key, aux);
    }
    @Override
    public void inc_NKijc(String key, int j, int c) {
        int[][] aux = this.NKijc.get(key);
        aux[j][c]++;
        this.NKijc.put(key, aux);
    }
    @Override
    public void inc_NJikc(String key, int k, int c) {
        int[][] aux = this.NJikc.get(key);
        aux[k][c]++;
        this.NJikc.put(key, aux);
    }

    public void print_Nijkc(String key) {
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
    
    public void print_NKijc(String key) {
        int[][] aux = this.NKijc.get(key);
        for (int c = 0; c < aux[0].length; c++) {
            for (int j = 0; j < aux.length; j++) {
            	System.out.print(aux[j][c] + " ");
            }
            System.out.println();
        }
    }

    public void print_NJikc(String key) {
        int[][] aux = this.NJikc.get(key);
        for (int c = 0; c < aux[0].length; c++) {
            for (int k = 0; k < aux.length; k++) {
            	System.out.print(aux[k][c] + " ");
            }
            System.out.println();
        }
    }
}