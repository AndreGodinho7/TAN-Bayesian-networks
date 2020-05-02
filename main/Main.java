package main;

import project.*;

import java.io.File;
import java.util.*;

public class Main {

	public static void main(String[] args) {

		String trainFilename = "train-enunciado.csv";
		int[] values;

		DataReader readTrainFile = new FileData();
		readTrainFile.openFile(trainFilename);

		// first sweep
		// get feature names
		((FileData)readTrainFile).setFeatures();
		((FileData)readTrainFile).initr_values();

		// get r values
		((FileData)readTrainFile).setR_Class();

		System.out.println(Arrays.toString(((FileData) readTrainFile).getR_values()));
		System.out.println(((FileData) readTrainFile).getNum_classes());

		Graph graph = new MyGraph((FileData)readTrainFile);
		graph.setNodes(); // TODO: or for loop setNodes(i) ?

		// second sweep
		readTrainFile.openFile(trainFilename);
		readTrainFile.passline(); // do not read features strings

		// update nodes
		while (true) {
			String line = readTrainFile.readline();
			if (line == null) break;
			values = Arrays.stream(line.split(",")).mapToInt(Integer::parseInt).toArray();
			graph.updateNodes(values);
		}

		// TODO: delete this when not needed anymore
		graph.printNodes();
		//Score s = new LLScore();


	}
}