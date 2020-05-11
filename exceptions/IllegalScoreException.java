package exceptions;

public class IllegalScoreException extends Exception {
    public IllegalScoreException() {
        super("Score indefined in input command.");
    }
}
