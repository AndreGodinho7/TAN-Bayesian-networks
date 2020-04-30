package main;

import project.GraphData;
import project.MyGraph;
import project.Node;

import java.util.*;

public class Main {

	public static void main(String[] args) {

		String trainFilename = "train-enunciado.csv";
		int[] r_values;
		String[] features;
		int[] values;
		int num_class = -1;
		int aux_class;
		HashMap<String, Integer> aux_map = new HashMap<String, Integer>();

		GraphData readTrainFile = new GraphData();
		readTrainFile.openFile(trainFilename);

		// first sweep
		// get feature names
		features = readTrainFile.readline().split(",");
		features = Arrays.copyOfRange(features, 0, features.length - 1);
		r_values = new int[features.length];
		for (int i=0; i < r_values.length;i++) r_values[i] = -1;

		// get r values
		while(true){
			String line = readTrainFile.readline();
			if (line == null) break;
			values = Arrays.stream(line.split(",")).mapToInt(Integer::parseInt).toArray();

			for (int i=0; i < values.length-1; i++){
				if (values[i] > r_values[i]) r_values[i] = values[i];
			}

			aux_class = values[values.length-1];
			if (aux_class > num_class) num_class = aux_class;
		}
		for (int i=0; i < r_values.length;i++) r_values[i]++;
		num_class++;

		System.out.println(Arrays.toString(r_values));
		System.out.println(num_class);

		MyGraph graph = new MyGraph();

		for (int i=0; i<features.length;i++){
			Node n = new Node(features[i], r_values[i]);
			n.setNijkc(i, features, r_values, num_class);
			graph.insertInList(n);
		}

		// second sweep
		readTrainFile.openFile(trainFilename);
		readTrainFile.passline(); // do not read features strings

		// increment counters of Nijkc
		while(true){
			String line = readTrainFile.readline();
			if (line == null) break;
			values = Arrays.stream(line.split(",")).mapToInt(Integer::parseInt).toArray();

			for (int i=0; i < values.length-1; i++) {
				aux_map.put(features[i], values[i]);
			}
			aux_class = values[values.length-1];

			LinkedList<Node> ns = graph.getListNodes();
			for(Node n : ns){
				for (String key : n.getNijkc().keySet()){
					if (n.getFeature_name().equals(key)){
						n.inc_Nijkc(key, 0, aux_map.get(key), aux_class);
					}
					else {
						n.inc_Nijkc(key, aux_map.get(n.getFeature_name()), aux_map.get(key), aux_class);
					}
				}
			}

		}

		// TODO: delete this when not needed anymore
		LinkedList<Node> ns = graph.getListNodes();
		for (Node n: ns){
			System.out.println("Node father: "+n.getFeature_name());
			for (String key : n.getNijkc().keySet()){
				System.out.println("Node son: "+key);
				n.print_Nijkc(key);
			}
		for(Node n : ns){
			n.computeNKijc();
			n.computeNJikc();
		}
		printNodeStructure(nodes);
	}
}