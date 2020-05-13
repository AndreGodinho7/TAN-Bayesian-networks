package metrics;

/**
 * Implements a method to calculate the F1-score given the precision and sensitivity obtained for the specified data.
 */
public class F1score extends ClassificationMetrics {
    private float f1score;
    private float precision;
    private float sensitivity;

    /**
     * Initializes class fields.
     *
     * @param labels        2-column matrix with target (first column) and predicted (second column) data
     * @param precision     obtained precision as a float for the target/predicted data
     * @param sensitivity   obtained sensitivity as a float for the target/predicted data
     */
    public F1score(int[][] labels, ClassificationMetrics precision, ClassificationMetrics sensitivity) {
        super(labels);
        this.precision = ((Precision) precision).getPrecision();
        this.sensitivity = ((Sensitivity) sensitivity).getSensitivity();
    }

    /**
     * Implements generic interface method to calculate the desired metric, in this case, the F1-score.
     *
     * @return  F1-score as a float value
     */
    @Override
    public float calculateMetric() {
        this.f1score = 2 * ((this.precision * this.sensitivity) / (this.precision + this.sensitivity));
        return this.f1score;
    }
}
