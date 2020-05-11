package exceptions;

public class IllegalNumberOfFeatureValuesException extends Exception{
    public IllegalNumberOfFeatureValuesException() {
        super("Number of feature values in sample do not match number of features.");
    }
}
