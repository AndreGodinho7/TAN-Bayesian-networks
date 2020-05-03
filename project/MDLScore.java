package project;

public class MDLScore implements Score{
	public static double log2(double N) {
		double result = (double)(Math.log(N) / Math.log(2.0));
		return result;
	}

	@Override
	public double calculate_score(Node father, Node son) {
		// father is i' ; son is i
		double weight = 0;
		String key_son = son.getFeature_name();
		int[][][] Nijkc = father.getNijkc(key_son);
		int[][] NJikc = father.getNJikc(key_son);
		int[][] NKijc = father.getNKijc(key_son);
		double [] Nc = Node.getNc();
		double N = Node.getN();

		for(int c=0; c < Nijkc[0][0].length; c++){
			for (int j=0; j < Nijkc.length; j++){
				for (int k=0; k < Nijkc[0].length ;k++){
					weight += ((Nijkc[j][k][c])/(N)) * log2((Nijkc[j][k][c]*Nc[c])/(NJikc[k][c]*NKijc[j][c]));
				}
			}
		}

		// s = Nc.length i.e. number of classes
		weight = weight - (Nc.length*0.5*(son.getR())*(father.getQ(son.getFeature_name())) *Math.log(N));
		return weight;
	}
}
