package metrics;

public abstract class ClassificationMetrics implements ClassificationMetricCalculation{
    protected int[][] labels;

    public ClassificationMetrics(int[][] labels) {
        this.labels = labels;
    }
}
