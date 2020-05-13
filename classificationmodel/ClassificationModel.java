package classificationmodel;

import classificationmodel.ClassificationModelInterface;


public abstract class ClassificationModel implements ClassificationModelInterface {
    String[] features;
    int num_classes;

    /**
     * Returns array of strings containing the names of the features.
     *
     * @return  Array of strings containing the names of the features
     */
    public String[] getFeatures() { return features; }

    /**
     * Returns number of classes.
     *
     * @return  Number of classes
     */
    public int getNum_classes() { return num_classes; }

    /**
     * Sets the names of the features.
     *
     * @param features  Array of strings containing the names of the features
     */
    public void setFeatures(String[] features) { this.features = features; }

    /**
     * Sets the number of classes.
     *
     * @param num_classes Number of classes
     */
    public void setNum_classes(int num_classes) { this.num_classes = num_classes; }

    /**
     * Trains a specific model, given the training data file name or path, and a hyperparameter.
     *
     * @param trainFilePath     Training data file name or path
     * @param hyperParameter    Hyperparameter
     */
    @Override
    public abstract void train(String trainFilePath, String hyperParameter);

    /**
     * Tests a model previously trained given the test file name or path. Returns a 2-column matrix, where the first
     * column corresponds to target classifications and the second column corresponds to predicted classifications.
     *
     * @param testFilePath  Test file name or path
     * @return              2-column matrix with target classifications (1st column) and predictions (2nd column)
     */
    @Override
    public abstract  int[][] predict(String testFilePath);
}
