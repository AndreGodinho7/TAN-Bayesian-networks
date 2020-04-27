package main;

//import project.DataReader;
import project.DataReader.ArrayData;

public class Main {

	public static void main(String[] args) {
		String /*trainFilename = "bias-train.csv",*/ testFilename = "bias-test.csv";
		
		//ReadToArray readTrainFile = new ReadToArray();
		ArrayData readTestFile = new ArrayData();
		
		readTestFile.openFile(testFilename);
		readTestFile.readFile();
		readTestFile.printData();
	}
}
