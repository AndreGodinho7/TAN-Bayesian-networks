package classificationmodel.bayesiannetwork.graph;

/**
 * Defines methods to implement counts.
 */
public interface Counts {

    /**
     * Sets the set of counts to be used.
     */
    public void setCounts();

    /**
     * Increments each count in the set of counts, given a new instance.
     *
     * @param sample    New instance (array of integer values for each feature)
     */
    public void incrementCounts(int[] sample);
}
