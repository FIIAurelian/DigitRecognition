package usr.fiiaurelian.digitrecognition.em;

import usr.fiiaurelian.digitrecognition.input.Instance;

import java.math.BigDecimal;

/**
 * Created by aurelian on 08.03.2017.
 */
public class ExpectationMaximizationBMM extends ExpectationMaximization {

    @Override
    protected void expectation() {
        for(int i = 0; i < dataset.getInstances().size(); i++) {
            BigDecimal totalProb = BigDecimal.ZERO;
            BigDecimal[] probs = new BigDecimal[clusters.length];
            for(int j = 0; j < clusters.length; j++) {
                probs[j] = clusters[j].getProbabilityForInstance(dataset.getInstances().get(i));
                totalProb = totalProb.add(probs[j]);
            }
            for(int j = 0; j < clusters.length; j++) {
                if(totalProb.compareTo(BigDecimal.ZERO) == 0) {
                    latentVariable[i][j] = 1.0 / clusters.length;
                } else {
                    latentVariable[i][j] = probs[j].divide(totalProb, 2, BigDecimal.ROUND_HALF_UP).doubleValue();
                }
            }
        }
    }

    @Override
    protected void maximization() {
        double[] membershipProbability = new double[clusters.length];
        double[][] featureProbability = new double[clusters.length][dataset.getColumnNames().length - 1];
        double totalProb = .0;
        for(int i = 0; i < dataset.getInstances().size(); i++) {
            for(int j = 0; j < clusters.length; j++) {
                totalProb += latentVariable[i][j];
                membershipProbability[j] += latentVariable[i][j];
            }

            Instance instance = dataset.getInstances().get(i);
            for(int j = 0; j < clusters.length; j++) {
                for(int k = 1; k < instance.getAttributes().length; k++) {
                    featureProbability[j][k-1] += latentVariable[i][j] * instance.getAttributes()[k];
                }
            }
        }
        for(int i = 0; i < clusters.length; i++) {
            for(int j = 1; j < dataset.getColumnNames().length; j++) {
                featureProbability[i][j-1] /= Math.max(membershipProbability[i], 1e-9);
            }
            clusters[i].setFeatureProbability(featureProbability[i]);
        }
        for(int i = 0; i < clusters.length; i++) {
            clusters[i].setMembershipProbability(membershipProbability[i] / totalProb);
        }
    }
}
