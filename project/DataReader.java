package project;

public interface DataReader {	
	void openFile(String filename);
	String readline();
	void passline();

}
