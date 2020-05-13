package exceptions;

/**
 * Exception is thrown when a sample has no label.
 * It also returns the number of that sample
 */
public class MissingClassInSampleException extends  Exception{
    public MissingClassInSampleException(int index) {
        super(String.format("Sample %d does not have a label.", index));
    }
}
