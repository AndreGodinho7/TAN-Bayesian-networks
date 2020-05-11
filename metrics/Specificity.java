package metrics;

public class Specificity extends ClassificationMetrics {
    private float specificity;
    private int trueClass;
    int tn;
    int fp;

    public Specificity(int[][] labels, int _trueClass) {
        super(labels);
        this.trueClass = _trueClass;
        this.tn = 0;
        this.fp = 0;
    }

    public void setTN(int tn) { this.tn = tn; }

    public void setFP(int fp) { this.fp = fp; }

    public void calcSpecificity() { this.specificity = (float) this.tn / (this.tn + this.fp); }

    @Override
    public float calculateMetric() {
        if ((this.tn == 0) && this.fp == 0) {
            for (int[] instance : this.labels) {
                if ((instance[0] == instance[1]) && instance[0] != this.trueClass) {
                    this.tn++;
                } else if ((instance[0] != instance[1]) && instance[1] == this.trueClass) {
                    this.fp++;
                }
            }
        }
        calcSpecificity();
        return this.specificity;
    }
}
