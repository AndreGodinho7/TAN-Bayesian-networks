package metrics;

/**
 * Implements methods to obtain the specificity for a given data set.
 */
public class Specificity extends ClassificationMetrics {
    private float specificity;
    private int positiveClass;
    int tn;
    int fp;

    /**
     * Initializes class fields.
     *
     * @param labels            2-column matrix with target (first column) and predicted (second column) data
     * @param _positiveClass    Class designated as positive for the computation of the precision
     */
    public Specificity(int[][] labels, int _positiveClass) {
        super(labels);
        this.positiveClass = _positiveClass;
        this.tn = 0;
        this.fp = 0;
    }

    /**
     * Sets number of true negative counts.
     *
     * @param tn    Number of true negative counts
     */
    public void setTN(int tn) { this.tn = tn; }

    /**
     * Sets number of false positive counts.
     *
     * @param fp    Number of false positive counts
     */
    public void setFP(int fp) { this.fp = fp; }

    /**
     * Calculates the specificity as a float.
     */
    public void calcSpecificity() { this.specificity = (float) this.tn / (this.tn + this.fp); }

    /**
     * Implements generic interface method to calculate the desired metric, in this case, the specificity.
     *
     * @return  Specificity as a float value
     */
    @Override
    public float calculateMetric() {
        if ((this.tn == 0) && this.fp == 0) {
            for (int[] instance : this.labels) {
                if ((instance[0] == instance[1]) && instance[0] != this.positiveClass) {
                    this.tn++;
                } else if ((instance[0] != instance[1]) && instance[1] == this.positiveClass) {
                    this.fp++;
                }
            }
        }
        calcSpecificity();
        return this.specificity;
    }
}
