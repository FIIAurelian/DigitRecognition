package usr.fiiaurelian.digitrecognition.em;

import usr.fiiaurelian.digitrecognition.input.Instance;

import java.math.BigDecimal;

/**
 * Created by aurelian on 08.03.2017.
 */
public class Cluster {

    private double membershipProbability;
    private double[] featureProbability;

    public double getMembershipProbability() {
        return membershipProbability;
    }

    public void setMembershipProbability(double membershipProbability) {
        this.membershipProbability = membershipProbability;
    }

    public double[] getFeatureProbability() {
        return featureProbability;
    }

    public void setFeatureProbability(double[] featureProbability) {
        this.featureProbability = featureProbability;
    }

    public BigDecimal getProbabilityForInstance(Instance instance) {
        BigDecimal probability = new BigDecimal(Math.max(membershipProbability, 1e-9));
        for(int i = 0; i < featureProbability.length; i++) {
            if(instance.getAttributes()[i+1] == 1) {
                probability = probability.multiply(new BigDecimal(Math.max(featureProbability[i], 1e-9)));
            } else {
                probability = probability.multiply(new BigDecimal(Math.max(1.0 - featureProbability[i], 1e-9)));
            }
        }
        return probability;
    }

    public static Cluster getWithClustersAndFeatures(int clusterNumber, int featureNumber) {
        Cluster cluster = new Cluster();
        cluster.membershipProbability = 1.0 / (double) clusterNumber;
        cluster.featureProbability = new double[featureNumber];
        return cluster;
    }
}
