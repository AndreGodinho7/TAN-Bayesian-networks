package project;

public class MyConfusionMatrix extends ClassificationMetrics {
    private int tp;
    private int tn;
    private int fp;
    private int fn;
    private int trueClass;

    public MyConfusionMatrix(int[][] labels, int trueClass) {
        super(labels);
        this.trueClass = trueClass;
    }

    public int getTP() { return tp; }

    public int getTN() { return tn; }

    public int getFP() { return fp; }

    public int getFN() { return fn; }

    @Override
    public float calculateMetric() {
        for (int[] instance : this.labels) {
            if ((instance[0] != instance[1]) && instance[1] == this.trueClass) {
                this.fp++;
            } else if ((instance[0] == instance[1]) && instance[0] == this.trueClass) {
                this.tp++;
            } else if ((instance[0] != instance[1]) && instance[0] == this.trueClass) {
                this.fn++;
            } else if ((instance[0] == instance[1]) && instance[0] != this.trueClass) {
                this.tn++;
            }
        }
        return 0;
    }
}
