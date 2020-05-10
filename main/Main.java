package main;

import project.*;

import java.io.File;
import java.util.*;

public class Main {

    public static void main(String[] args) {

        String trainFilename = "bias-train.csv";
        String testFilename = "bias-test.csv";
        String score = "LL";

        ClassificationModel model = new BayesianNetwork();
        model.train(trainFilename, score);
        int[][] data2Measure = model.predict(testFilename);

        // TODO: this is for testing, change later
        Accuracy acc = new Accuracy(data2Measure);
        float accuracy = acc.metric();
        System.out.println("Accuracy: " + accuracy);

        Specificity spec = new Specificity(data2Measure);
        spec.setTrueClass(0);
        float specificity1 = spec.metric();
        spec.setTrueClass(1);
        float specificity2 = spec.metric();

        System.out.println("Specificity (Class 0): " + specificity1);
        System.out.println("Specificity (Class 1): " + specificity2);





    }
}