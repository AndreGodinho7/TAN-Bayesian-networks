package io;

import exceptions.IllegalNumberOfParametersException;

public class Input {
    private String trainFilename;
    private String testFilename;
    private String score;

    public Input(String[] args) {
        this.trainFilename = args[0];
        this.testFilename = args[1];
        this.score = args[2];
    }
    // Getters:
    public String getTrainFilename() { return this.trainFilename; }
    public String getTestFilename() { return this.testFilename; }
    public String getScore() { return this.score; }
}
