package project;

import org.w3c.dom.CDATASection;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;

public class Node {
    private static int[] Nc;

    private String feature_name;
    private HashMap<String, int[][][]> Nijkc;
    private HashMap<String, int[][]> NKijc;
    private HashMap<String, int[][]> NJikc;
    private HashMap<String, Integer> q;
    private int r;

    private static int[] r_arr;

    public Node(String feature_name, int r) {
        this.feature_name = feature_name;
        this.r = r;
    }

    public void setNijkc(int keys, String[] features, int[]r_values, int classes){
        HashMap<String, int[][][]> map = new HashMap<String, int[][][]>();
        map.put(features[keys], new int[1][r_values[keys]][classes]);

        for (int i=keys+1; i<features.length;i++){
            map.put(features[i], new int[r_values[keys]][r_values[i]][classes]);
        }
        System.out.println("Hashmap of feature:"+features[keys]);
        map.entrySet().forEach(entry->{
            System.out.println("key:"+entry.getKey() + " value:" + "int["+entry.getValue().length+"]"+"["+entry.getValue()[0].length+"]"+"["+entry.getValue()[0][0].length+"]");
        });
        System.out.println();
        this.Nijkc = map;
    }

    public void setNKijc(int keys, String[] features, int[]r_values, int classes){
        HashMap<String, int[][]> map = new HashMap<String, int[][]>();
        map.put(features[keys], new int[1][classes]);
        for (int i=keys+1; i<features.length;i++){
            map.put(features[i], new int[r_values[keys]][classes]);
        }
        System.out.println("NKijc Hashmap of feature "+features[keys]);
        map.entrySet().forEach(entry->{System.out.println("key:" + entry.getKey() + " value:"
                + "int["+ entry.getValue().length + "]"
                + "[" + entry.getValue()[0].length + "]");});
        this.NKijc = map;
    }

    public void setNJikc(int keys, String[] features, int[]r_values, int classes){
        HashMap<String, int[][]> map = new HashMap<String, int[][]>();
        map.put(features[keys], new int[r_values[keys]][classes]);
        for (int i=keys+1; i<features.length;i++){
            map.put(features[i], new int[r_values[i]][classes]);
        }
        System.out.println("NJikc Hashmap of feature "+features[keys]);
        map.entrySet().forEach(entry->{System.out.println("key:" + entry.getKey() + " value:"
                + "int[" + entry.getValue().length + "]"
                + "[" + entry.getValue()[0].length + "]");});
        this.NJikc = map;
    }

    // Getter methods:
    public String getFeature_name() {return feature_name;}
    public HashMap<String, int[][][]> getNijkc() {return Nijkc;}

    /**
     * Increment counts of Nijkc as file lines are read
     * @param key
     * @param j
     * @param k
     * @param c
     */
    public void inc_Nijkc(String key, int j, int k, int c){
        int[][][] aux = this.Nijkc.get(key);
        aux[j][k][c]++;
        this.Nijkc.put(key, aux);
    }

    public void computeNKijc() {
        for (String _key : this.Nijkc.keySet()) {
            int[][][] aux_Nijkc = this.Nijkc.get(_key);
            int[][] aux_NKijc = this.NKijc.get(_key);

            for (int j = 0; j < aux_Nijkc.length; j++) {
                for (int c = 0; c < aux_Nijkc[0][0].length; c++) {
                    for (int k = 0; k < aux_Nijkc[0].length; k++) {
                        aux_NKijc[j][c] += aux_Nijkc[j][k][c];
                        this.NKijc.put(_key, aux_NKijc);
                    }
                }
            }
        }
    }


    public void computeNJikc() {
        for (String _key : this.Nijkc.keySet()) {
            int[][][] aux_Nijkc = this.Nijkc.get(_key);
            int[][] aux_NJikc = this.NJikc.get(_key);

            for (int k = 0; k < aux_Nijkc[0].length; k++) {
                for (int c = 0; c < aux_Nijkc[0][0].length; c++) {
                    for (int j = 0; j < aux_Nijkc.length; j++) {
                        aux_NJikc[k][c] += aux_Nijkc[j][k][c];
                        this.NJikc.put(_key, aux_NJikc);
                    }
                }
            }
        }
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