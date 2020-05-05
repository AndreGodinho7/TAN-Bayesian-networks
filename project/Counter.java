package project;

import java.util.Map;

public interface Counter {
    public void setNijkc(int keys, String[] features, int[]r_values, int classes);
    public void setNKijc(int keys, String[] features, int[]r_values, int classes);
    public void setNJikc(int keys, String[] features, int[]r_values, int classes);
    public void setQ(int keys, String[] features, int r);
    public void setR(int r);

    public Map<String, int[][][]> getNijkcMap();
    public int[][][] getNijkc(String key);
    public int[][] getNJikc(String key);
    public int[][] getNKijc(String key);
    public int getQ(String key);
    public String getFeature_name();

    public int getR();
    public void inc_Nijkc(String key, int j, int k, int c);
    public void inc_NKijc(String key, int j, int c);
    public void inc_NJikc(String key, int k, int c);



}
