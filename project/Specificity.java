package project;

public class Specificity implements ClassificationMetrics {
    int data[][];
    private float specificity;
    private int trueClass;
    int tn;
    int fp;

    public Specificity(int[][] _data) {
        this.data = _data;
        this.specificity = 0;

    }

    public void setTrueClass(int _trueClass) { this.trueClass = _trueClass; }
    public void calcSpecificity() { this.specificity = (float) this.tn / (this.tn + this.fp); }

    @Override
    public float metric() {
        for (int[] instance : this.data) {
            if ((instance[0] == instance[1]) && instance[0] != this.trueClass) {
                this.tn++;
            } else if ((instance[0] != instance[1]) && instance[1] == this.trueClass) {
                this.fp++;
            }
        }
        calcSpecificity();
        return this.specificity;
    }
}
