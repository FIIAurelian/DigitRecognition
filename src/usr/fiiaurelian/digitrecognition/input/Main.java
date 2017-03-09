package usr.fiiaurelian.digitrecognition.input;

import usr.fiiaurelian.digitrecognition.em.ExpectationMaximization;
import usr.fiiaurelian.digitrecognition.em.ExpectationMaximizationBMM;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by aurelian on 08.03.2017.
 */
public class Main {

    public static void main(String[] args) throws IOException {
        Dataset dataset = Dataset.loadFromCsv("train.csv", ",", true, 0.05);
        System.out.println("Size: " + dataset.getInstances().size());

        List<Instance> newInstances = new ArrayList<>();
        for(int i = 0; i < dataset.getInstances().size(); i++) {
            Instance instance = dataset.getInstances().get(i);
            if(instance.getAttributes()[0] > 1 && instance.getAttributes()[0] < 5) {
                newInstances.add(instance);
            }
            for(int j = 1; j < instance.getAttributes().length; j++) {
                if(instance.getAttributes()[j] > 127) {
                    instance.getAttributes()[j] = 1;
                } else {
                    instance.getAttributes()[j] = 0;
                }
            }
        }
        //dataset.getInstances().clear();
        //dataset.getInstances().addAll(newInstances);

        for(int i = 0; i < 10; i++) {
            final int k = i;
            long cnt = dataset.getInstances().stream().filter(instance -> instance.getAttributes()[0] == k).count();
            System.out.println("Class: " + k + ", Instances: " + cnt);
        }

        ExpectationMaximization em = new ExpectationMaximizationBMM()
                .withIterations(20)
                .withDataset(dataset)
                .withClusters(10);
        double[][] latentVariable = em.compute();

        int[][] confusionMatrix = new int[10][10];
        for(int i = 0; i < latentVariable.length; i++) {
            double maximum = latentVariable[i][0];
            int cluster = 0;
            for(int j = 0; j < latentVariable[i].length; j++) {
                if(latentVariable[i][j] > maximum) {
                    maximum = latentVariable[i][j];
                    cluster = j;
                }
            }
            confusionMatrix[cluster][dataset.getInstances().get(i).getAttributes()[0]]++;
        }

        for(int i = 0; i < 10; i++) {
            for(int j = 0; j < 10; j++) {
                System.out.print(confusionMatrix[i][j] + " ");
            }
            System.out.println();
        }
    }
}
