package classificationmodel;

/**
 * Defines methods to implement a classification model.
 */
public interface ClassificationModelInterface {

    /**
     * Trains a specific model, given the training data file name or path, and a hyperparameter.
     *
     * @param trainFilePath     Training data file name or path
     * @param hyperParameter    Hyperparameter
     */
    public void train(String trainFilePath, String hyperParameter);

    /**
     * Tests a model previously trained given the test file name or path. Returns a 2-column matrix, where the first
     * column corresponds to target classifications and the second column corresponds to predicted classifications.
     *
     * @param testFilePath  Test file name or path
     * @return              2-column matrix with target classifications (1st column) and predictions (2nd column)
     */
    public int[][] predict (String testFilePath);
}
