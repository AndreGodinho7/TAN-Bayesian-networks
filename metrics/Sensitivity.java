package metrics;

public class Sensitivity extends ClassificationMetrics {
    private float sensitivity;
    private int positiveClass;
    int fn;
    int tp;

    public Sensitivity(int[][] labels, int positiveClass) {
        super(labels);
        this.positiveClass = positiveClass;
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
