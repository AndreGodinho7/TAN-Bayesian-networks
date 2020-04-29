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

    public Node(String feature_name, HashMap<String, int[][][]> nijkc, int r) {
        this.feature_name = feature_name;
        this.Nijkc = nijkc;
        this.r = r;
    }

    private static int[] r_arr;

    public static int[] getR_arr() {
        return r_arr;
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