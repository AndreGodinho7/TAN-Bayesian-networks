package classificationmodel.bayesiannetwork.graph;

public interface Score {
	public double calculate_score(Counts father, Counts son);

}