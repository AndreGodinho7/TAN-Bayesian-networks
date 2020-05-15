package metrics;

/**
 * Implements methods to obtain the Weighted specificity for a given data set.
 */
public class WeightedSpecificity extends  ClassificationMetrics{
    private float weightedSpec;
    private float[] specificities;
    private double[] Nc;

    /**
     * Initializes number of instances in each class field and specificities values of all classes
     *
     *
     * @param labels    2-column matrix with target (first column) and predicted (second column) data
     * @param specificities specificity values of classes
     * @param Nc value of instances per class
     */
    public WeightedSpecificity(int[][] labels, float[] specificities, double[] Nc) {
        super(labels);
        this.specificities = specificities;
        this.Nc = Nc;
    }

    /**
     * Implements generic interface method to calculate the desired metric, in this case, the weighted specificity.
     *
     * @return  accuracy as a float value
     */
    @Override
    public float calculateMetric() {
        float numerator = 0;
        float denominator = 0;
        for (int i=0; i<this.specificities.length;i++){
            numerator += this.Nc[i]*this.specificities[i];
            denominator += Nc[i];
        }
        this.weightedSpec = numerator/denominator;
        return this.weightedSpec;
    }
}
