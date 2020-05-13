package exceptions;

public class MissingClassInSampleException extends  Exception{
    public MissingClassInSampleException(int index) {
        super(String.format("Sample %d does not have a label.", index));
    }
}
