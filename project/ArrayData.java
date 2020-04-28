package project;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class ArrayData implements DataReader {

	int[][] featureMatrix;
	int[] classArray;
	
	Scanner inputStream, scanFileSize;
	
	/**
	 * Open file
	 * @param filename: name of file to open
	 */
	public void openFile(String filename) {
		try {
			scanFileSize = new Scanner(new File(filename));
			inputStream = new Scanner(new File(filename));
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Read the input file	
	 */
	public void readFile() {

		int nRows = 0, nCols = 0, fileRow = 0;
		String[] headerOfFile = inputStream.next().split(",");
		nCols = headerOfFile.length;
		scanFileSize.next();
		
		//sweep file to count number of rows
		while (scanFileSize.hasNext()) {
			scanFileSize.next();
			nRows++;
		}
		scanFileSize.close();
		System.out.println("nr. of rows: "+nRows+"\nnr. of columns: "+nCols);
		
		this.featureMatrix = new int[nRows][nCols];
		this.classArray = new int[nRows];
		System.out.println("feature matrix dims: " + featureMatrix.length + "x" + featureMatrix[0].length);
		System.out.println("class array dim: " + classArray.length);
					
		// sweep file again, now to save the data
		while (inputStream.hasNext()) {
			String line = inputStream.next();
			int[] values = Arrays.stream(line.split(",")).mapToInt(Integer::parseInt).toArray();
			this.featureMatrix[fileRow] = values;
			this.classArray[fileRow] = values[values.length - 1];
			fileRow++;
		}
		inputStream.close();
	}
	
	public void printData() {
		for (int row = 0; row < this.featureMatrix.length; row++) {
			for (int col = 0; col < this.featureMatrix[row].length; col++) {
				System.out.print(this.featureMatrix[row][col] + " ");
			}
			System.out.print("  " + this.classArray[row]);
			System.out.println();
		}
	}
}

