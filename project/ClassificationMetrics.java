package project;

public abstract class ClassificationMetrics implements ClassificationMetricCalculation{
    int labels[][];

    public ClassificationMetrics(int[][] labels) {
        this.labels = labels;
    }
}
