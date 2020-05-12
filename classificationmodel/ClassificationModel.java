package classificationmodel;

import classificationmodel.ClassificationModelInterface;

public abstract  class ClassificationModel implements ClassificationModelInterface {
    private String[] features;
    private int num_classes;

    public String[] getFeatures() { return features; }
    public int getNum_classes() { return num_classes; }

    public void setFeatures(String[] features) { this.features = features; }
    public void setNum_classes(int num_classes) { this.num_classes = num_classes; }

    @Override
    public abstract void train(String trainFilePath, String hyperParameter);

    @Override
    public abstract  int[][] predict(String testFilePath);
}
