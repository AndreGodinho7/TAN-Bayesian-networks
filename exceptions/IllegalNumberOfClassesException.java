package exceptions;

/**
 * Exception is thrown when classes do not assume adjacent values starting from 0.
 */
public class IllegalNumberOfClassesException extends Exception {
    public IllegalNumberOfClassesException() {
        super("Class values are not acceptable. The class values must be continuous.");
    }
}
