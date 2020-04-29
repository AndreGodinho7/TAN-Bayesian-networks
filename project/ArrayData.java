package project;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class ArrayData implements DataReader {

	int[][] featureMatrix;
	int[] classArray;
	int num_class;
	String[] features;

	Scanner inputStream, scanFileSize;

	public int getNum_class() {
		return num_class;
	}

	public int[][] getFeatureMatrix() {
		return featureMatrix;
	}

	public String[] getFeatures() {
		return features;
	}

	public int[] getClassArray() {
		return classArray;
	}

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
		this.features = Arrays.copyOfRange(headerOfFile, 0, headerOfFile.length - 1);
		scanFileSize.next();

		//sweep file to count number of rows
		while (scanFileSize.hasNext()) {
			scanFileSize.next();
			nRows++;
		}
		scanFileSize.close();
		System.out.println("nr. of rows: "+nRows+"\nnr. of columns: "+nCols+"\n");

		this.featureMatrix = new int[nRows][nCols];
		this.classArray = new int[nRows];

		// sweep file again, now to save the data
		while (inputStream.hasNext()) {
			String line = inputStream.next();
			int[] values = Arrays.stream(line.split(",")).mapToInt(Integer::parseInt).toArray();
			this.featureMatrix[fileRow] = Arrays.copyOfRange(values, 0, values.length - 1);
			this.classArray[fileRow] = values[values.length - 1];
			fileRow++;
		}
		this.num_class = Arrays.stream(classArray).max().getAsInt()+1;

		inputStream.close();
	}

	public void printData() {
		System.out.println("Features: " + Arrays.deepToString(this.features) + "\n");

		for (int row = 0; row < this.featureMatrix.length; row++) {
			for (int col = 0; col < this.featureMatrix[row].length; col++) {
				System.out.print(this.featureMatrix[row][col] + " ");
			}
			System.out.print("  " + this.classArray[row]);
			System.out.println();
		}
		System.out.println("# classes: "+this.num_class);
	}
}

