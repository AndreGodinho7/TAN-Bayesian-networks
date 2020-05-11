package classificationmodel;

public interface ClassificationModel {
    public void train(String trainFilePath, String hyperParameter);
    public int[][] predict (String testFilePath);
}
