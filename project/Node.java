package project;

import org.w3c.dom.CDATASection;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;

public class Node {
    private static int[] Nc;

    private String feature_name;
    private HashMap<String, int[][][]> Nijkc;
    private HashMap<String, int[][]> NKijc; //TODO: extend constructor to initialize this one
    private HashMap<String, int[][]> NJikc; //TODO: extend constructor to initialize this one
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

    public void inc_Nijkc(String key, int first, int second, int third){
        int[][][] aux = this.Nijkc.get(key);
        aux[first][second][third]++;
        this.Nijkc.put(key, aux);
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

    public HashMap<String, int[][][]> getNijkc() {
        return Nijkc;
    }

    public String getFeature_name() {
        return feature_name;
    }

    public static void init_r(int[][] dataset) {
        r_arr = new int[dataset[0].length];
        for (int row = 0; row < dataset.length; row++) {
            for (int col = 0; col < dataset[row].length; col++) {
                if (dataset[row][col] == r_arr[col]) r_arr[col]++;
            }
        }
        System.out.println(Arrays.toString(r_arr));
    }


}