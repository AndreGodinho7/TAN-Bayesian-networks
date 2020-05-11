package project;

public class LLScore implements Score {

    public static double log2(double N) {
        double result = (double)(Math.log(N) / Math.log(2.0));
        return result;
    }

    @Override
    public double calculate_score(Counts father, Counts son) {
        // father is i' ; son is i
        float weight = 0;
        String key_father = ((Node)father).getFeature_name();
        int[][][] Nijkc = ((Node)son).getNijkc(key_father);
        int[][] NJikc = ((Node)son).getNJikc(key_father);
        int[][] NKijc = ((Node)son).getNKijc(key_father);
        double [] Nc = Node.getNc();
        double N = Node.getN();

        for(int c=0; c < Nijkc[0][0].length; c++){
            for (int j=0; j < Nijkc.length; j++){
                for (int k=0; k < Nijkc[0].length ;k++){
                    if (Nijkc[j][k][c] == 0 || NJikc[k][c] == 0 || NKijc[j][c] == 0) continue;
                    weight += ((Nijkc[j][k][c])/(N)) * log2((Nijkc[j][k][c]*Nc[c])/(NJikc[k][c]*NKijc[j][c]));
                }
            }
        }
        return weight;
    }
}
