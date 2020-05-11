package metrics;

public class ConfusionMatrixDerivations {
    private int[][] labels;
    private int tp;
    private int tn;
    private int fp;
    private int fn;
    private int positiveClass;

    public ConfusionMatrixDerivations(int[][] labels, int positiveClass) {
        this.positiveClass = positiveClass;
        this.labels = labels;
    }

    public int getTP() {
        return tp;
    }

    public int getTN() {
        return tn;
    }

    public int getFP() {
        return fp;
    }

    public int getFN() {
        return fn;
    }

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
