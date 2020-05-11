package classificationmodel.bayesiannetwork.graph;

public interface Counts {
    public void setCounts();
    public void incrementCounts(int[] sample); // sample of input dataset
}
