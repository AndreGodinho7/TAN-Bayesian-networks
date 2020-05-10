package project;

public class F1score extends ClassificationMetrics {
    private float f1score;
    private float precision;
    private float sensitivity;

    public F1score(int[][] labels, ClassificationMetrics precision, ClassificationMetrics sensitivity) {
        super(labels);
        this.precision = ((Precision) precision).getPrecision();
        this.sensitivity = ((Sensitivity) sensitivity).getSensitivity();
    }

    @Override
    public float calculateMetric() {
        this.f1score = 2*((this.precision*this.sensitivity) / (this.precision+this.sensitivity));
        return this.f1score;
    }
}
