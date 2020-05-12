package classificationmodel;

public interface ClassificationModelInterface {
    public void train(String trainFilePath, String hyperParameter);
    public int[][] predict (String testFilePath);
}
