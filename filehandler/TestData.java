package filehandler;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class TestData<nrLines> implements DataReader {

    private Scanner inputStream;
    private int nrLines;
    private int[] classTarget;

    public int[] getClassTarget() { return this.classTarget; }
    public int getNrLines() { return this.nrLines; }

    /**
     * Opens a file given the name or path of the test file intended to be opened.
     *
     * @param filename  Name or path of the input test file
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
     * Reads a line of the test file.
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
     * Sweeps the file and count the number of lines.
     */
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

