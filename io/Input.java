package io;

import exceptions.IllegalNumberOfParametersException;

/**
 * Save command line input arguments to strings. The accepted arguments are: the name of the train file, the name of the
 * test file and a string "LL" or "MDL" specifying the type of score to be used (log-likelihood or minimum description
 * length).
 */
public class Input {
    private String trainFilename;
    private String testFilename;
    private String score;

    /**
     * Saves input arguments from command line to specified strings.
     * @param args                                command line input arguments (array of strings)
     * @throws IllegalNumberOfParametersException Thrown exception
     */
    public Input(String[] args) throws IllegalNumberOfParametersException {
        if (args.length != 3) throw new IllegalNumberOfParametersException();
        this.trainFilename = args[0];
        this.testFilename = args[1];
        this.score = args[2];
    }
    // Getters:

    /**
     * Get name/path of train file from command line args.
     * @return  :   name/path of the train file
     */
    public String getTrainFilename() { return this.trainFilename; }

    /**
     * Get name/path of test file from command line args.
     * @return  :   name/path of the test file
     */
    public String getTestFilename() { return this.testFilename; }

    /**
     * Get score type (LL or MDL) from command line args.
     * @return  :   score type (LL or MDL)
     */
    public String getScore() { return this.score; }
}
