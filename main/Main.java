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
        model.predict(testFilename);

    }
}