package metrics;

/**
 * Implements methods to obtain the accuracy for a given data set.
 */
public class Accuracy extends ClassificationMetrics {
    private float accuracy;
    private int totalInstances;
    private int correctPredictions;

    /**
     * Initializes class fields and determines total number of instances based on the length of the target/predicted
     * data.
     *
     * @param labels    2-column matrix with target (first column) and predicted (second column) data
     */
    public Accuracy(int[][] labels) {
        super(labels);
        this.accuracy = 0;
        this.totalInstances = labels.length;
        this.correctPredictions = 0;
    }

    /**
     * Returns the accuracy as a float value.
     *
     * @return  accuracy as float value
     */
    public float getAccuracy() { return this.accuracy; }

    /**
     * Calculates the accuracy as a float value based on the total correct predictions and total instances.
     */
    public void calcAccuracy() { this.accuracy = (float) this.correctPredictions / this.totalInstances; }

    /**
     * Implements generic interface method to calculate the desired metric, in this case, the accuracy.
     *
     * @return  accuracy as a float value
     */
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
