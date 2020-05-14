package metrics;

/**
 * Implements methods to obtain the Weighted sensitivity for a given data set.
 */
public class weightedSensitivity extends  ClassificationMetrics{
    private float weightedSens;
    private float[] sensitivities;
    private double[] Nc;

    /**
     * Initializes number of instances in each class field and sensitivity values of all classes
     *
     *
     * @param labels    2-column matrix with target (first column) and predicted (second column) data
     * @param sensitivities sensitivity values of classes
     * @param Nc value of instances per class
     */
    public weightedSensitivity(int[][] labels, float[] sensitivities, double[] Nc) {
        super(labels);
        this.sensitivities = sensitivities;
        this.Nc = Nc;
    }

    /**
     * Implements generic interface method to calculate the desired metric, in this case, the weighted sensitivity.
     *
     * @return  accuracy as a float value
     */
    @Override
    public float calculateMetric() {
        float numerator = 0;
        float denominator = 0;
        for (int i=0; i<this.sensitivities.length;i++){
            numerator += this.Nc[i]*this.sensitivities[i];
            denominator += Nc[i];
        }
        this.weightedSens = numerator/denominator;
        return this.weightedSens;
    }
}
