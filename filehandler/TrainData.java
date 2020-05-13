package filehandler;

import exceptions.IllegalNumberOfClassesException;
import exceptions.MissingClassInSampleException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Read the input file with training data. Determine the following parameters: number of features, number of values each
 * feature can take, number of instances, number of classes, number of occurrences of each class.
 */
public class TrainData implements DataReader {

    private String[] features;
    private int[] r_values;
    private double N;
    private int num_classes;
    private double[] Nc;
    private Scanner inputStream;

    /**
     * Returns array of strings where strings correspond to names of features.
     *
     * @return  array of strings
     */
    public String[] getFeatures() {
        return features;
    }

    /**
     * Returns array of integers where an integer corresponds to the total number of values a feature can take.
     *
     * @return  array of integers
     */
    public int[] getR_values() {
        return r_values;
    }

    /**
     * Returns total number of instances.
     *
     * @return  number of instances
     */
    public double getN() { return N; }

    /**
     * Returns number of occurrences of each class.
     *
     * @return  number of occurrences of each class
     */
    public double[] getNc() { return Nc; }

    /**
     * Returns total number of classes.
     *
     * @return  number of classes
     */
    public int getNum_classes() { return num_classes; }

    /**
     * toString method of the general specifications of the training data.
     *
     * @return  string with the specifications of the training data
     */
    @Override
    public String toString() {
        return "FileData{" +
                "features=" + Arrays.toString(features) +
                ", r_values=" + Arrays.toString(r_values) +
                ", N=" + N +
                ", num_classes=" + num_classes +
                ", Nc=" + Arrays.toString(Nc) +
                '}';
    }

    public void print(){
        System.out.println(this.toString());
    }

    /**
     * Opens a file given the name or path of the training file intended to be opened.
     *
     * @param filename  Name or path of the input training file
     */
    @Override
    public void openFile(String filename) {
        try {
            inputStream = new Scanner(new File(filename));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads a line of the training file.
     *
     * @return  pointer to the next line in the file or null if the end of the file was reached
     */
    @Override
    public String readline() {
        if (inputStream.hasNext()){
            return inputStream.next();
        }
        else{
            inputStream.close();
            return null;
        }
    }

    /**
     * Moves the pointer to the next line of the file.
     */
    @Override
    public void passline() {
        inputStream.next();
    }

    /**
     * Splits first line of the file into an array of strings, which correspond to the names of the features. Excludes
     * last element which is the identifier of the class column.
     */
    public void setFeatures(){
        String[] features = this.readline().split(",");
        this.features = Arrays.copyOfRange(features, 0, features.length - 1);
    }

    /**
     * Initializes an integer array with the same number of elements as the number of features with -1. This array will
     * store the number of values each feature can take.
     */
    public void initr_values(){
        this.r_values = new int[this.features.length];
        for (int i = 0; i < this.r_values.length; i++) this.r_values[i] = -1;
    }

    /**
     * Goes through training file and calculates the number of instances, number of occurrences of each class and number
     * of classes. Throws exceptions in case an instance lacks one or more classes, and in case class values are not
     * adjacent starting from 0 (e.g. classes can take values 0, 1, etc. but cannot take values 0, 2).
     *
     * @throws IllegalNumberOfClassesException  Class numbering is invalid ( e.g. 0, 1, 3)
     * @throws MissingClassInSampleException    instance lacks one or more classes exception
     */
    public void setData() throws IllegalNumberOfClassesException, MissingClassInSampleException {
        int aux_class = 0;
        int[] values;
        int instances=0;
        Map<Integer, Double> nc = new HashMap<>();
        while (true) {
            String line = this.readline();
            if (line == null) break;
            instances++;
            values = Arrays.stream(line.split(",")).mapToInt(Integer::parseInt).toArray();

            if (values.length-1 != features.length) throw new MissingClassInSampleException(instances);

            for (int i = 0; i < values.length - 1; i++) {
                if (values[i] > this.r_values[i]) this.r_values[i] = values[i];
            }

            aux_class = values[values.length - 1];
            Double counter = nc.get(aux_class);
            if (counter == null){
                nc.put(aux_class, 1.0);
            }
            else {
                counter = nc.get(aux_class);
                counter++;
                nc.put(aux_class, counter);
            }
        }

        int maxClass = 0;
        for (int num_class : nc.keySet()) {
            if (num_class > maxClass) {
                maxClass = num_class;
            }
        }
        if (maxClass != nc.size()-1) throw new IllegalNumberOfClassesException();

        for (int i = 0; i < r_values.length; i++) r_values[i]++;

        this.Nc = new double[nc.size()];
        for (int j = 0; j < nc.size(); j++){ this.Nc[j] = nc.get(j); }
        this.N = instances;
        this.num_classes = nc.size();
    }
}
