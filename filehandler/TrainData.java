package filehandler;

import exceptions.IllegalNumberOfClassesException;
import exceptions.IllegalNumberOfFeatureValuesException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class TrainData implements DataReader {

    private String[] features;
    private int[] r_values;
    private double N;
    private int num_classes;
    private double[] Nc;
    Scanner inputStream;

    public String[] getFeatures() {
        return features;
    }

    public int[] getR_values() {
        return r_values;
    }

    public double getN() { return N; }

    public double[] getNc() { return Nc; }

    public int getNum_classes() { return num_classes; }

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

    @Override
    public void openFile(String filename) {
        try {
            //scanFileSize = new Scanner(new File(filename));
            inputStream = new Scanner(new File(filename));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

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

    @Override
    public void passline() {
        inputStream.next();
    }

    public void setFeatures(){
        String[] features = this.readline().split(",");
        this.features = Arrays.copyOfRange(features, 0, features.length - 1);
    }

    public void initr_values(){
        this.r_values = new int[this.features.length];
        for (int i = 0; i < this.r_values.length; i++) this.r_values[i] = -1;
    }

    public void setData() throws IllegalNumberOfFeatureValuesException, IllegalNumberOfClassesException {
        int aux_class = 0;
        int[] values;
        int instances=0;
        Map<Integer, Double> nc = new HashMap<>();
        while (true) {
            String line = this.readline();
            if (line == null) break;
            instances++;
            values = Arrays.stream(line.split(",")).mapToInt(Integer::parseInt).toArray();

            if (values.length-1 != features.length) throw new IllegalNumberOfFeatureValuesException();

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
        for (int i = 0; i < r_values.length; i++) r_values[i]++;
        this.Nc = new double[nc.size()];
        for (int j = 0; j < nc.size(); j++){ this.Nc[j] = nc.get(j); }
        this.N = instances;
        this.num_classes = nc.size();

        int maxClass = 0;
        for (int num_class : nc.keySet()) {
            if (num_class > maxClass) {
                maxClass = num_class;
            }
        }
        if (maxClass != nc.size()-1) throw new IllegalNumberOfClassesException();
    }
}
