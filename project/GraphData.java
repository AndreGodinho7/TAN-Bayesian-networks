package project;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class GraphData implements DataReader {

    Scanner inputStream, scanFileSize;
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
}
