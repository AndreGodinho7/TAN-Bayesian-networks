package io;

import classificationmodel.bayesiannetwork.BayesianNetwork;
import classificationmodel.ClassificationModel;
import classificationmodel.ClassificationModelInterface;
import metrics.*;

/**
 * Generate and print the output: network structure, predictions, time to build and time to test, accuracy, specificity,
 * sensitivity, and F1-score.
 */
public class Output {
    /**
     * Print the output according to demanded specifications.
     * @param model             :   Model built and used for classification.
     * @param target_predicted  :   2-column matrix where the first column corresponds to target classifications and the
     *                              second column corresponds to classifications predicted by the model.
     * @param trainTime         :   Time to build and train the model.
     * @param testTime          :   Time to test the model.
     */
    public static void print(ClassificationModelInterface model, int[][] target_predicted, double trainTime, double testTime) {
        int line = 1;

        //TODO: remover esta string no fim
        System.out.println("\n\nFINAL OUTPUT (tirar esta string depois):");

        ((BayesianNetwork)model).printBayesianNodes();
        System.out.println(String.format("Time to build:\t\t\t%.4f ms", trainTime/1e6));
        System.out.println("Testing the classifier:");
        for (int[] instance: target_predicted) {
            System.out.println(String.format("-> instance %d:\t\t\t%d", line, instance[1]));
            line++;
        }
        System.out.println(String.format("Time to test:\t\t\t%.4f ms", testTime/1e6));

        ClassificationMetrics accuracy = new Accuracy(target_predicted);
        System.out.println(String.format("Resume:\t\t\t\t\t%.4f", accuracy.calculateMetric()));

        for (int positiveClass = 0; positiveClass < ((ClassificationModel)model).getNum_classes(); positiveClass++) {
            ConfusionMatrixDerivations m = new ConfusionMatrixDerivations(target_predicted, positiveClass);
            m.calculateDerivations();

            ClassificationMetrics specificity = new Specificity(target_predicted, positiveClass);
            ((Specificity)specificity).setTN(((ConfusionMatrixDerivations)m).getTN());
            ((Specificity)specificity).setFP(((ConfusionMatrixDerivations)m).getFP());
            specificity.calculateMetric();

            ClassificationMetrics sensitivity = new Sensitivity(target_predicted, positiveClass);
            ((Sensitivity)sensitivity).setTP(((ConfusionMatrixDerivations)m).getTP());
            ((Sensitivity)sensitivity).setFN(((ConfusionMatrixDerivations)m).getFN());
            sensitivity.calculateMetric();

            ClassificationMetrics precision = new Precision(target_predicted, positiveClass);
            ((Precision)precision).setFP(((ConfusionMatrixDerivations)m).getFP());
            ((Precision)precision).setTP(((ConfusionMatrixDerivations)m).getTP());
            precision.calculateMetric();

            ClassificationMetrics f1 = new F1score(target_predicted, precision, sensitivity);

            System.out.println(String.format("\t\t\t\t\t\tPositive class = %d: %.4f, %.4f, %.4f",
                    positiveClass, specificity.calculateMetric(), sensitivity.calculateMetric(), f1.calculateMetric()));
        }
    }
}
