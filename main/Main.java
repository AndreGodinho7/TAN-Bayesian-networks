package main;

//import project.DataReader;
import project.ArrayData;
import project.Node;

import java.util.*;

public class Main {

	// TODO: create an util class for this methods
	private static HashMap<String, int[][][]> createNijkc(int keys, String[] features, int[]r_values, int classes){
		HashMap<String, int[][][]> map = new HashMap<String, int[][][]>();
		map.put(features[keys], new int[1][r_values[keys]][classes]);

		for (int i=keys+1; i<features.length;i++){
			map.put(features[i], new int[r_values[keys]][r_values[i]][classes]);
		}
		/*System.out.println("Nijkc Hashmap of feature "+features[keys]);
		map.entrySet().forEach(entry->{
			System.out.println("key:" + entry.getKey() + " value:"
								+ "int[" + entry.getValue().length + "]"
								+ "[" + entry.getValue()[0].length + "]"
								+ "["+entry.getValue()[0][0].length + "]");});
		System.out.println();*/
		return map;
	}
	
	private static HashMap<String, int[][]> createNKijc(int keys, String[] features, int[]r_values, int classes){
		HashMap<String, int[][]> map = new HashMap<String, int[][]>();
		map.put(features[keys], new int[1][classes]);
		for (int i=keys+1; i<features.length;i++){
			map.put(features[i], new int[r_values[keys]][classes]);
		}
		/*System.out.println("NKijc Hashmap of feature "+features[keys]);
		map.entrySet().forEach(entry->{System.out.println("key:" + entry.getKey() + " value:"
														+ "int["+ entry.getValue().length + "]"
														+ "[" + entry.getValue()[0].length + "]");});
		System.out.println();*/
		return map;
	}
	
	private static HashMap<String, int[][]> createNJikc(int keys, String[] features, int[]r_values, int classes){
		HashMap<String, int[][]> map = new HashMap<String, int[][]>();
		map.put(features[keys], new int[r_values[keys]][classes]);
		for (int i=keys+1; i<features.length;i++){
			map.put(features[i], new int[r_values[i]][classes]);
		}
		/*System.out.println("NJikc Hashmap of feature "+features[keys]);
		map.entrySet().forEach(entry->{System.out.println("key:" + entry.getKey() + " value:"
														+ "int[" + entry.getValue().length + "]"
														+ "[" + entry.getValue()[0].length + "]");});
		System.out.println();*/
		return map;
	}
	
	// TODO: complete this method to print hashmaps NKijc and NJikc
	public static void printNodeStructure(LinkedList<Node> nodes) {
		for (Node n: nodes){
			System.out.println("Node father: "+n.getFeature_name());
			for (String key : n.getNijkc().keySet()){
				System.out.println("Node son: "+key);
				System.out.println("--Nijkc:");
				n.print_Nijkc(key);
				System.out.println();
				System.out.println("--NKijc:");
				n.print_NKijc(key);
				System.out.println();
				System.out.println("--NJikc:");
				n.print_NJikc(key);
				System.out.println();
			}
		}
	}
	
	// main
	public static void main(String[] args) {

		String trainFilename = "train-enunciado.csv";
		// testFilename = "bias-test.csv";
		
		//ReadToArray readTrainFile = new ReadToArray();
		ArrayData readTestFile = new ArrayData();
		
		readTestFile.openFile(trainFilename);
		readTestFile.readFile();
		//readTestFile.printData();

		Node.init_r(readTestFile.getFeatureMatrix());

		LinkedList<Node> nodes = new LinkedList<>();
		for (int i=0; i<readTestFile.getFeatures().length;i++){
			HashMap<String, int[][][]> map_Nijkc = createNijkc(i, readTestFile.getFeatures(), Node.getR_values(), readTestFile.getNum_class());
			HashMap<String, int[][]> map_NKijc =  createNKijc(i, readTestFile.getFeatures(), Node.getR_values(), readTestFile.getNum_class());
			HashMap<String, int[][]> map_NJikc =  createNJikc(i, readTestFile.getFeatures(), Node.getR_values(), readTestFile.getNum_class());	
			Node n = new Node(readTestFile.getFeatures()[i], Node.getR_values()[i], map_Nijkc, map_NKijc, map_NJikc);
			nodes.add(n);
		}

		int[][] dataset = readTestFile.getFeatureMatrix();
		HashMap<String, Integer> aux_map = new HashMap<String, Integer>();
		for (int row = 0; row < dataset.length; row++) {
			for (int col = 0; col <dataset[0].length;col++){
				aux_map.put(readTestFile.getFeatures()[col], dataset[row][col]);
			}
			aux_map.put("class", readTestFile.getClassArray()[row]);

			for(Node n : nodes){
				//int num_keys = n.getNijkc().size();
				for (String key : n.getNijkc().keySet()){
					if (n.getFeature_name().equals(key)){
						n.inc_Nijkc(key, 0, aux_map.get(key), aux_map.get("class"));
					}
					else {
						n.inc_Nijkc(key, aux_map.get(n.getFeature_name()), aux_map.get(key), aux_map.get("class"));
					}
				}
			}
		}
		for(Node n : nodes){
			n.computeNKijc();
			n.computeNJikc();
		}
		printNodeStructure(nodes);
	}
}