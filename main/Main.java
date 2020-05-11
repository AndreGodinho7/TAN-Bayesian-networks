package main;

import classificationmodel.bayesiannetwork.BayesianNetwork;
import classificationmodel.ClassificationModel;
import io.Output;

public class Main {

    public static void main(String[] args) {

        String trainFilename = "bias-train.csv";
        String testFilename = "bias-test.csv";
        String score = "LL";

        ClassificationModel model = new BayesianNetwork();

        double startTimeTrain = System.nanoTime();
        model.train(trainFilename, score);
        double stopTimeTrain = System.nanoTime();

        double startTimeTest = System.nanoTime();
        int[][] data2Measure = model.predict(testFilename);
        double stopTimeTest = System.nanoTime();

        double trainTime = stopTimeTrain - startTimeTrain;
        double testTime = stopTimeTest - startTimeTest;

        Output.print(model, data2Measure, trainTime, testTime);

        // TODO: this is for testing, change later
        /*ClassificationMetrics accuracy = new Accuracy(data2Measure);

        ClassificationMetrics confmat0 = new MyConfusionMatrix(data2Measure, 0);
        ClassificationMetrics confmat1 = new MyConfusionMatrix(data2Measure, 1);

        ClassificationMetrics specificity0 = new Specificity(data2Measure, 0);
        ClassificationMetrics specificity1 = new Specificity(data2Measure, 1);

        ClassificationMetrics sensitivity0 = new Sensitivity(data2Measure, 0);
        ClassificationMetrics sensitivity1 = new Sensitivity(data2Measure, 1);

        ClassificationMetrics precision0 = new Precision(data2Measure, 0);
        ClassificationMetrics precision1 = new Precision(data2Measure, 1);

        float acc = accuracy.calculateMetric();
        confmat0.calculateMetric();
        confmat1.calculateMetric();

        ((Specificity)specificity0).setTN(((MyConfusionMatrix)confmat0).getTN());
        ((Specificity)specificity0).setFP(((MyConfusionMatrix)confmat0).getFP());
        ((Specificity)specificity1).setTN(((MyConfusionMatrix)confmat1).getTN());
        ((Specificity)specificity1).setFP(((MyConfusionMatrix)confmat1).getFP());
        float spec0 = specificity0.calculateMetric();
        float spec1 = specificity1.calculateMetric();

        ((Sensitivity)sensitivity0).setTP(((MyConfusionMatrix)confmat0).getTP());
        ((Sensitivity)sensitivity0).setFN(((MyConfusionMatrix)confmat0).getFN());
        ((Sensitivity)sensitivity1).setTP(((MyConfusionMatrix)confmat1).getTP());
        ((Sensitivity)sensitivity1).setFN(((MyConfusionMatrix)confmat1).getFN());
        float sens0 = sensitivity0.calculateMetric();
        float sens1 = sensitivity1.calculateMetric();

        ((Precision)precision0).setFP(((MyConfusionMatrix)confmat0).getFP());
        ((Precision)precision0).setTP(((MyConfusionMatrix)confmat0).getTP());
        ((Precision)precision1).setFP(((MyConfusionMatrix)confmat1).getFP());
        ((Precision)precision1).setTP(((MyConfusionMatrix)confmat1).getTP());
        float prec0 = precision0.calculateMetric();
        float prec1 =precision1.calculateMetric();

        ClassificationMetrics f1score0 = new F1score(data2Measure, precision0, sensitivity0);
        ClassificationMetrics f1score1 = new F1score(data2Measure, precision1, sensitivity1);

        float f1s0 = f1score0.calculateMetric();
        float f1s1 = f1score1.calculateMetric();

        System.out.println("Accuracy: " + acc);
        System.out.println("Specificity (Class 0): " + spec0);
        System.out.println("Specificity (Class 1): " + spec1);
        System.out.println("Sensitivity (Class 0): " + sens0);
        System.out.println("Sensitivity (Class 1): " + sens1);
        System.out.println("F1-score (Class 0): " + f1s0);
        System.out.println("F1-score (Class 1): " + f1s1);*/

    }
}