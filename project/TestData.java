package project;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class TestData<nrLines> implements DataReader {

    private Scanner inputStream;
    private int nrLines;
    private int[] classTarget;

    public int[] getClassTarget() { return this.classTarget; }
    public int getNrLines() { return this.nrLines; }

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

    public void scanNrLines() {
        this.nrLines = 0;
        passline();
        while (true){
            String instance = this.readline();
            if (instance == null) break;
            this.nrLines++;
        }
    }
}

