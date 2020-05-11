package exceptions;

public class IllegalNumberOfClassesException extends Exception {
    public IllegalNumberOfClassesException() {
        super("Class values are not acceptable. The class values must be continuous.");
    }
}
