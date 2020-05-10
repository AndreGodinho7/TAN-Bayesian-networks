package project;

public class Precision extends ClassificationMetrics {
    private float precision;
    private int trueClass;
    int fp;
    int tp;

    public Precision(int[][] labels, int _trueClass) {
        super(labels);
        this.trueClass = _trueClass;
        this.fp = 0;
        this.tp = 0;
    }

    public void setFP(int fp) { this.fp = fp; }

    public void setTP(int tp) { this.tp = tp; }

    public float getPrecision() { return precision; }

    public void calcSpecificity() { this.precision = (float) this.tp / (this.tp + this.fp); }

    @Override
    public float calculateMetric() {
        if ((this.tp == 0) && this.fp == 0) {
            for (int[] instance : this.labels) {
                if ((instance[0] != instance[1]) && instance[1] == this.trueClass) {
                    this.fp++;
                } else if ((instance[0] == instance[1]) && instance[0] == this.trueClass) {
                    this.tp++;
                }
            }
        }
        calcSpecificity();
        return this.precision;
    }
}
