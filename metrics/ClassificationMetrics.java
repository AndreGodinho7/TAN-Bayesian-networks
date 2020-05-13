package metrics;
/**
 * Declares the method to calculate a classification metric
 * Contains the predicted and true label of each test sample so that the sub class can calculate the Metric
 * according to those values
 */
public abstract class ClassificationMetrics implements ClassificationMetricCalculation{
    protected int[][] labels;

    /**
     * method to calculate a classification metric.
     * @param labels    True labels and predicted labels of the test set
     */
    public ClassificationMetrics(int[][] labels) {
        this.labels = labels;
    }
}
