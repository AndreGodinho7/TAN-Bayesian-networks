package project;

public class MDLScore implements Score{
	public static double log2(double N) {
		double result = (double)(Math.log(N) / Math.log(2.0));
		return result;
	}

	@Override
	public double calculate_score(Counter father, Counter son) {
		// father is i' ; son is i
		double weight = 0;
		//String key_son = son.getFeature_name();
		String key_father = father.getFeature_name();
		int[][][] Nijkc = son.getNijkc(key_father);
		int[][] NJikc = son.getNJikc(key_father);
		int[][] NKijc = son.getNKijc(key_father);
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

		// s = Nc.length i.e. number of classes
		weight = weight - (Nc.length*0.5*(son.getR()-1)*(son.getQ(father.getFeature_name())-1) *Math.log(N));
		return weight;
	}
}
