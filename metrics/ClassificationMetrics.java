package metrics;
/**
 * Declares the method to calculate a classification metric
 * Contains the predicted and true label of each test sample so that the sub class can calculate the Metric
 * according to those values
 */
public abstract class ClassificationMetrics implements ClassificationMetricCalculation{
    protected int[][] labels;

    /**
     * Constructor of a classification metric.
     * @param labels    True labels and predicted labels of the test set
     */
    public ClassificationMetrics(int[][] labels) {
        this.labels = labels;
    }

    /**
     * Overrides the interface calculateMetric method
     * @return  float value of the metric
     */
    @Override
    public abstract float calculateMetric();
}
