package io;

import classificationmodel.bayesiannetwork.BayesianNetwork;
import classificationmodel.ClassificationModel;
import classificationmodel.ClassificationModelInterface;
import metrics.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Generate and print the output: network structure, predictions, time to build and time to test, accuracy, specificity,
 * sensitivity, and F1-score.
 */
public class Output {
    /**
     * Print the output according to demanded specifications.
     *
     * @param model            :   Model built and used for classification.
     * @param target_predicted :   2-column matrix where the first column corresponds to target classifications and the
     *                         second column corresponds to classifications predicted by the model.
     * @param trainTime        :   Time to build and train the model.
     * @param testTime         :   Time to test the model.
     */
    public static void print(ClassificationModelInterface model, int[][] target_predicted, double trainTime, double testTime) {
        int line = 1;
        Map<Integer, Double> map = new HashMap<>();
        for (int[] instance : target_predicted) {
            Double counter = map.get(instance[0]);
            if (counter == null) {
                map.put(instance[0], 1.0);
            } else {
                counter = map.get(instance[0]);
                counter++;
                map.put(instance[0], counter);
            }
        }
        double[] Nc = new double[map.size()];
        for (int j = 0; j < map.size(); j++) {
            Nc[j] = map.get(j);
        }

        ((BayesianNetwork) model).printBayesianNodes();
        System.out.println(String.format("Time to build:\t\t%.4f ms", trainTime / 1e6));
        System.out.println("Testing the classifier:");
        for (int[] instance : target_predicted) {
            System.out.println(String.format("-> instance %d:\t\t%d", line, instance[1]));
            line++;
        }
        System.out.println(String.format("Time to test:\t\t%.4f ms", testTime / 1e6));

        ClassificationMetrics accuracy = new Accuracy(target_predicted);
        System.out.print(String.format("Resume:\t\t\t%.3f, ", accuracy.calculateMetric()));


        float[] specificities = new float[Nc.length];
        float[] sensitivities = new float[Nc.length];
        float[] f1measures = new float[Nc.length];

        for (int positiveClass = 0; positiveClass < ((ClassificationModel) model).getNum_classes(); positiveClass++) {
            ConfusionMatrixDerivations m = new ConfusionMatrixDerivations(target_predicted, positiveClass);
            m.calculateDerivations();

            ClassificationMetrics specificity = new Specificity(target_predicted, positiveClass);
            ((Specificity) specificity).setTN(((ConfusionMatrixDerivations) m).getTN());
            ((Specificity) specificity).setFP(((ConfusionMatrixDerivations) m).getFP());
            specificity.calculateMetric();
            specificities[positiveClass] = ((Specificity) specificity).getSpecificity();

            ClassificationMetrics sensitivity = new Sensitivity(target_predicted, positiveClass);
            ((Sensitivity) sensitivity).setTP(((ConfusionMatrixDerivations) m).getTP());
            ((Sensitivity) sensitivity).setFN(((ConfusionMatrixDerivations) m).getFN());
            sensitivity.calculateMetric();
            sensitivities[positiveClass] = ((Sensitivity) sensitivity).getSensitivity();


            ClassificationMetrics precision = new Precision(target_predicted, positiveClass);
            ((Precision) precision).setFP(((ConfusionMatrixDerivations) m).getFP());
            ((Precision) precision).setTP(((ConfusionMatrixDerivations) m).getTP());
            precision.calculateMetric();

            ClassificationMetrics f1 = new F1score(target_predicted, precision, sensitivity);
            f1.calculateMetric();
            f1measures[positiveClass] = ((F1score) f1).getF1score();

//            System.out.println(String.format("\t\t\tPositive class = %d: %.4f, %.4f, %.4f",
//                    positiveClass, specificity.calculateMetric(), sensitivity.calculateMetric(), f1.calculateMetric()));
        }
        ClassificationMetrics w_sensitivity = new weightedSensitivity(target_predicted, specificities, Nc);
        ClassificationMetrics w_specificity = new weightedSpecificity(target_predicted, sensitivities, Nc);
        ClassificationMetrics w_f1measure = new weightedF1measure(target_predicted, f1measures, Nc);

        System.out.print("[");
        for (int positiveClass = 0; positiveClass < ((ClassificationModel) model).getNum_classes(); positiveClass++) {
            System.out.print(String.format("%d: %.3f, ", positiveClass, specificities[positiveClass]));
        }
        System.out.print(String.format("%.3f], [", w_specificity.calculateMetric()));
        for (int positiveClass = 0; positiveClass < ((ClassificationModel) model).getNum_classes(); positiveClass++) {
            System.out.print(String.format("%d: %.3f, ", positiveClass, sensitivities[positiveClass]));
        }
        System.out.print(String.format("%.3f], [",w_sensitivity.calculateMetric()));
        for (int positiveClass = 0; positiveClass < ((ClassificationModel) model).getNum_classes(); positiveClass++) {
            System.out.print(String.format("%d: %.3f, ",positiveClass, f1measures[positiveClass]));
        }
        System.out.print(String.format("%.3f]\n", w_f1measure.calculateMetric()));
    }
}
