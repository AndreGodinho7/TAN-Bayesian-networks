package filehandler;

/**
 * Interface with generic methods for reading data from file
 */
public interface DataReader {
    void openFile(String filename);
    String readline();
    void passline();

}
