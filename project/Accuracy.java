package project;

public class Accuracy implements ClassificationMetrics {
    int[][] data;
    private float accuracy;
    int totalInstances;
    int correctPredictions;

    public Accuracy(int[][] _data) {
        this.data = _data;
        this.accuracy = 0;
        this.totalInstances = _data.length;
        this.correctPredictions = 0;
    }

    //public float getAccuracy() { return this.accuracy; }
    public void calcAccuracy() { this.accuracy = (float) this.correctPredictions / this.totalInstances; }

    @Override
    public float metric() {

        for (int[] instance : data) {
            if (instance[0] == instance[1]) {
                this.correctPredictions++;
            }
        }
        calcAccuracy();
        return this.accuracy;
    }
}
