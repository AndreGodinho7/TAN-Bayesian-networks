package project;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class FileData implements DataReader {

    private String[] features;
    private int[] r_values;
    private int num_class;
    Scanner inputStream;

    public String[] getFeatures() {
        return features;
    }

    public int[] getR_values() {
        return r_values;
    }

    public int getNum_classes() {
        return num_class;
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

    public void setR_Class(){
        int aux_class = 0;
        int[] values;
        this.num_class = -1;
        while (true) {
            String line = this.readline();
            if (line == null) break;
            values = Arrays.stream(line.split(",")).mapToInt(Integer::parseInt).toArray();

            for (int i = 0; i < values.length - 1; i++) {
                if (values[i] > this.r_values[i]) this.r_values[i] = values[i];
            }

            aux_class = values[values.length - 1];
            if (aux_class > this.num_class) this.num_class = aux_class;
        }
        for (int i = 0; i < r_values.length; i++) r_values[i]++;
        this.num_class++;
    }

}
