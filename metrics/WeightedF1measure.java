package metrics;

/**
 * Implements methods to obtain the Weighted F1-measure for a given data set.
 */
public class WeightedF1measure extends ClassificationMetrics{
    private float weightedF1s;
    private float[] f1measures;
    private double[] Nc;

    /**
     * Initializes number of instances in each class field and f1-measures values of all classes
     *
     *
     * @param labels    2-column matrix with target (first column) and predicted (second column) data
     * @param f1measures f1-measure values of classes
     * @param Nc value of instances per class
     */
    public WeightedF1measure(int[][] labels, float[] f1measures, double[] Nc) {
        super(labels);
        this.f1measures = f1measures;
        this.Nc = Nc;
    }

    /**
     * Implements generic interface method to calculate the desired metric, in this case, the weighted f1-measure.
     *
     * @return  accuracy as a float value
     */
    @Override
    public float calculateMetric() {
        float numerator = 0;
        float denominator = 0;
        for (int i=0; i<this.f1measures.length;i++){
            numerator += this.Nc[i]*this.f1measures[i];
            denominator += Nc[i];
        }
        this.weightedF1s = numerator/denominator;
        return this.weightedF1s;
    }
}
