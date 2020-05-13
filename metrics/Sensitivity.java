package metrics;

/**
 * Implements methods to obtain the sensitivity for a given data set.
 */
public class Sensitivity extends ClassificationMetrics {
    private float sensitivity;
    private int positiveClass;
    int fn;
    int tp;

    /**
     * Initializes class fields.
     *
     * @param labels        2-column matrix with target (first column) and predicted (second column) data
     * @param positiveClass Class designated as positive for the computation of the precision
     */
    public Sensitivity(int[][] labels, int positiveClass) {
        super(labels);
        this.positiveClass = positiveClass;
        this.fn = 0;
        this.tp = 0;
    }

    /**
     * Sets number of false negative counts.
     *
     * @param fn    Number of false negative counts
     */
    public void setFN(int fn) { this.fn = fn; }

    /**
     * Sets number of true positive counts.
     *
     * @param tp    Number of true positive counts
     */
    public void setTP(int tp) { this.tp = tp; }

    /**
     * Returns the sensitivity as a float.
     *
     * @return  sensitivity as a float
     */
    public float getSensitivity() { return sensitivity; }

    /**
     * Calculates the sensitivity as a float.
     */
    public void calcSensitivity() { this.sensitivity = (float) this.tp / (this.tp + this.fn); }

    /**
     * Implements generic interface method to calculate the desired metric, in this case, the sensitivity.
     *
     * @return  Sensitivity as a float value
     */
    @Override
    public float calculateMetric() {
        if ((this.tp == 0) && this.fn == 0) {
            for (int[] instance : this.labels) {
                if (instance[1] == this.positiveClass) {
                    if (instance[0] == instance[1]) { this.tp++; }
                } else {
                    if (instance[0] != this.positiveClass && instance[0] != instance[1]) { this.fn++; }
                }
            }
        }
        calcSensitivity();
        return this.sensitivity;
    }
}
