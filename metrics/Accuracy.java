package metrics;

public class Accuracy extends ClassificationMetrics {
    private float accuracy;
    int totalInstances;
    int correctPredictions;

    public Accuracy(int[][] labels) {
        super(labels);
        this.accuracy = 0;
        this.totalInstances = labels.length;
        this.correctPredictions = 0;
    }

    //public float getAccuracy() { return this.accuracy; }
    public void calcAccuracy() { this.accuracy = (float) this.correctPredictions / this.totalInstances; }

    @Override
    public float calculateMetric() {
        for (int[] instance : this.labels) {
            if (instance[0] == instance[1]) {
                this.correctPredictions++;
            }
        }
        calcAccuracy();
        return this.accuracy;
    }
}
