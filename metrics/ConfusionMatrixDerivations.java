package metrics;

/**
 * Implements a method to calculate the confusion matrix given the classification data (target/predicted) and the
 * positive class.
 */
public class ConfusionMatrixDerivations {
    private int[][] labels;
    private int tp;
    private int tn;
    private int fp;
    private int fn;
    private int positiveClass;

    /**
     * Initializes input (target/predicted) data and the class to be selected as positive.
     *
     * @param labels        2-column matrix of integers, first column is target data, second column is predicted data
     * @param positiveClass Class designated as positive for the computation of the confusion matrix
     */
    public ConfusionMatrixDerivations(int[][] labels, int positiveClass) {
        this.positiveClass = positiveClass;
        this.labels = labels;
    }

    /**
     * Returns true positive counts as an integer.
     *
     * @return true positive counts
     */
    public int getTP() {
        return tp;
    }

    /**
     * Returns true negative counts as an integer.
     *
     * @return true negative counts
     */
    public int getTN() {
        return tn;
    }

    /**
     * Returns false positive counts as an integer.
     *
     * @return false positive counts
     */
    public int getFP() {
        return fp;
    }

    /**
     * Returns false negative counts as an integer.
     *
     * @return false negative counts
     */
    public int getFN() {
        return fn;
    }

    /**
     * Calculate the true positive, true negative, false positive and false negative counts.
     */
    public void calculateDerivations() {
        for (int[] instance : this.labels) {
            if (instance[1] == this.positiveClass) {
                if (instance[0] == instance[1]) {
                    this.tp++;
                } else {
                    this.fp++;
                }
            } else {
                if (instance[0] == instance[1]) {
                    this.tn++;
                } else {
                    if (instance[0] != this.positiveClass) {
                        this.tn++;
                    } else {
                        this.fn++;
                    }
                }
            }
        }
    }
}
