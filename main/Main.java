package main;

import project.*;

import java.util.*;

public class Main {

	public static void main(String[] args) {

		String trainFilename = "train-enunciado.csv";
		int[] r_values;
		String[] features;
		int[] values;
		int num_class = -1;
		int aux_class;
		Map<String, Integer> aux_map = new HashMap<String, Integer>();

		DataReader readTrainFile = new GraphData();
		readTrainFile.openFile(trainFilename);

		// first sweep
		// get feature names
		features = readTrainFile.readline().split(",");
		features = Arrays.copyOfRange(features, 0, features.length - 1);
		r_values = new int[features.length];
		for (int i = 0; i < r_values.length; i++) r_values[i] = -1;

		// get r values
		while (true) {
			String line = readTrainFile.readline();
			if (line == null) break;
			values = Arrays.stream(line.split(",")).mapToInt(Integer::parseInt).toArray();

			for (int i = 0; i < values.length - 1; i++) {
				if (values[i] > r_values[i]) r_values[i] = values[i];
			}

			aux_class = values[values.length - 1];
			if (aux_class > num_class) num_class = aux_class;
		}
		for (int i = 0; i < r_values.length; i++) r_values[i]++;
		num_class++;

		System.out.println(Arrays.toString(r_values));
		System.out.println(num_class);

		Graph graph = new MyGraph(features.length);
		graph.setNodes(features, r_values, num_class);

		// second sweep
		readTrainFile.openFile(trainFilename);
		readTrainFile.passline(); // do not read features strings

		// update nodes
		while (true) {
			String line = readTrainFile.readline();
			if (line == null) break;
			values = Arrays.stream(line.split(",")).mapToInt(Integer::parseInt).toArray();

			for (int i = 0; i < values.length - 1; i++) {
				aux_map.put(features[i], values[i]);
			}
			aux_class = values[values.length - 1];
			graph.updateNodes(aux_map, aux_class);
		}

		// TODO: delete this when not needed anymore
		graph.printNodes();

	}
}