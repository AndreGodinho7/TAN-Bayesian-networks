package metrics;

/**
 * Implements a method to calculate the precision for specified data set.
 */
public class Precision extends ClassificationMetrics {
    private float precision;
    private int positiveClass;
    int fp;
    int tp;

    /**
     * Initializes class fields.
     *
     * @param labels            2-column matrix with target (first column) and predicted (second column) data
     * @param _positiveClass    Class designated as positive for the computation of the precision
     */
    public Precision(int[][] labels, int _positiveClass) {
        super(labels);
        this.positiveClass = _positiveClass;
        this.fp = 0;
        this.tp = 0;
    }

    /**
     * Sets number of false positive counts.
     *
     * @param fp    Number of false positive counts
     */
    public void setFP(int fp) { this.fp = fp; }

    /**
     * Sets number of true positive counts.
     *
     * @param fp    Number of true positive counts
     */
    public void setTP(int tp) { this.tp = tp; }

    /**
     * Returns the precision as a float.
     *
     * @return  precision as a float
     */
    public float getPrecision() { return precision; }

    /**
     * Calculates the precision as a float.
     */
    public void calcPrecision() { this.precision = (float) this.tp / (this.tp + this.fp); }

    /**
     * Implements generic interface method to calculate the desired metric, in this case, the precision.
     *
     * @return  Precision as a float value
     */
    @Override
    public float calculateMetric() {
        if ((this.tp == 0) && this.fp == 0) {
            for (int[] instance : this.labels) {
                if (instance[1] == this.positiveClass) {
                    if (instance[0] == instance[1]) {
                        this.tp++;
                    } else {
                        this.fp++;
                    }
                }
            }
        }
        calcPrecision();
        return this.precision;
    }
}
