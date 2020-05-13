package main;

import classificationmodel.bayesiannetwork.BayesianNetwork;
import classificationmodel.ClassificationModelInterface;
import exceptions.IllegalNumberOfParametersException;
import io.Input;
import io.Output;

/**
 * Main class of the program.
 */
public class Main {

    public static void main(String[] args) {
        try {
            if (args.length != 3) throw new IllegalNumberOfParametersException();
            Input input = new Input(args);
            String trainFilename = input.getTrainFilename();
            String testFilename = input.getTestFilename();
            String score = input.getScore();

            ClassificationModelInterface model = new BayesianNetwork();

            double startTimeTrain = System.nanoTime();
            model.train(trainFilename, score);
            double stopTimeTrain = System.nanoTime();

            double startTimeTest = System.nanoTime();
            int[][] data2Measure = model.predict(testFilename);
            double stopTimeTest = System.nanoTime();

            double trainTime = stopTimeTrain - startTimeTrain;
            double testTime = stopTimeTest - startTimeTest;

            Output.print(model, data2Measure, trainTime, testTime);

        } catch (IllegalNumberOfParametersException e) {
            e.printStackTrace();
            System.exit(-1);

        }



    }
}