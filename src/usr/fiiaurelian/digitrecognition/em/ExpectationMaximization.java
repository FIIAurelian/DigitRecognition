package usr.fiiaurelian.digitrecognition.em;

import usr.fiiaurelian.digitrecognition.input.Dataset;

/**
 * Created by aurelian on 08.03.2017.
 */
public abstract class ExpectationMaximization {

    protected int iterations;
    protected Dataset dataset;
    protected Cluster[] clusters;
    protected double[][] latentVariable;

    protected abstract void expectation();
    protected abstract void maximization();

    public ExpectationMaximization withIterations(int iterations) {
        this.iterations = iterations;
        return this;
    }

    public ExpectationMaximization withDataset(Dataset dataset) {
        this.dataset = dataset;
        return this;
    }

    public ExpectationMaximization withClusters(int clusterNumber) {
        clusters = new Cluster[clusterNumber];
        return this;
    }

    protected void init() {
        for(int i = 0; i < clusters.length; i++) {
            this.clusters[i] = Cluster.getWithClustersAndFeatures(clusters.length, dataset.getColumnNames().length - 1);
            double sum = .0;
            for(int j = 0; j < this.clusters[i].getFeatureProbability().length; j++) {
                this.clusters[i].getFeatureProbability()[j] = 0.25 + 0.5 * Math.random();
                sum = sum + this.clusters[i].getFeatureProbability()[j];
            }
        }
        latentVariable = new double[dataset.getInstances().size()][clusters.length];
        for(int i = 0; i < latentVariable.length; i++) {
            double prob = Math.random();
            double current = .0;
            for(int j = 0; j < latentVariable[i].length; j++) {
                double nxt = current + 1.0 / latentVariable[i].length;
                if(prob < nxt) {
                    latentVariable[i][j] = 1.0;
                    break;
                } else {
                    current = nxt;
                }
            }
        }
    }

    public double[][] compute() {
        init();
        for(int i = 0; i < iterations; i++) {
            expectation();
            maximization();
        }
        return latentVariable;
    }
}
