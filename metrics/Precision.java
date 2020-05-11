package metrics;

public class Precision extends ClassificationMetrics {
    private float precision;
    private int positiveClass;
    int fp;
    int tp;

    public Precision(int[][] labels, int _positiveClass) {
        super(labels);
        this.positiveClass = _positiveClass;
        this.fp = 0;
        this.tp = 0;
    }

    public void setFP(int fp) { this.fp = fp; }

    public void setTP(int tp) { this.tp = tp; }

    public float getPrecision() { return precision; }

    public void calcPrecision() { this.precision = (float) this.tp / (this.tp + this.fp); }

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
