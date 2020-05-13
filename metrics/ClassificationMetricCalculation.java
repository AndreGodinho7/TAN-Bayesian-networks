package metrics;

/**
 * Declares the method to calculate a classification metric
 */
public interface ClassificationMetricCalculation {
    /**
     * method to calculate a classification metric
     * @return
     */
    public float calculateMetric();
}
