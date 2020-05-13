package exceptions;

/**
 * Exception is raised when number of command line input parameters exceeds 3.
 */
public class IllegalNumberOfParametersException extends Exception {
    public IllegalNumberOfParametersException() {
        super("The program needs 3 input parameters. A filename of a dataset from which a classifier is going to be learned, a filename of a test set from which the learned classifier is going to be tested and the hyperparameter to be used to build the classifier");
    }
}
