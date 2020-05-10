package project;

public class Sensitivity extends ClassificationMetrics {
    private float sensitivity;
    private int trueClass;
    int fn;
    int tp;

    public Sensitivity(int[][] labels, int _trueClass) {
        super(labels);
        this.trueClass = _trueClass;
        this.fn = 0;
        this.tp = 0;
    }

    public void setFN(int fn) { this.fn = fn; }

    public void setTP(int tp) { this.tp = tp; }

    public float getSensitivity() { return sensitivity; }

    public void calcSensitivity() { this.sensitivity = (float) this.tp / (this.tp + this.fn); }

    @Override
    public float calculateMetric() {
        if ((this.tp == 0) && this.fn == 0) {
            for (int[] instance : this.labels) {
                if ((instance[0] != instance[1]) && instance[0] == this.trueClass) {
                    this.fn++;
                } else if ((instance[0] == instance[1]) && instance[0] == this.trueClass) {
                    this.tp++;
                }
            }
        }
        calcSensitivity();
        return this.sensitivity;
    }
}
