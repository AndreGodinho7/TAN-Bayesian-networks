package exceptions;

/**
 * Exception is thrown when a string different from "LL" or "MDL" is used to specify the score.
 */
public class IllegalScoreException extends Exception {
    public IllegalScoreException() {
        super("Score undefined in input command.");
    }
}
