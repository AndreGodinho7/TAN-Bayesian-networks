package metrics;

/**
 * Declares the method to calculate a classification metric
 */
public interface ClassificationMetricCalculation {
    /**
     * method to calculate a classification metric
     * @return  metric value as a float
     */
    public float calculateMetric();
}
