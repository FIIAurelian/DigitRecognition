package usr.fiiaurelian.digitrecognition.input;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by aurelian on 08.03.2017.
 */
public class Dataset {

    private String[] columnNames;
    private List<Instance> instances;

    public Dataset() {
        instances = new ArrayList<>();
    }

    public List<Instance> getInstances() {
        return instances;
    }

    public String[] getColumnNames() {
        return columnNames;
    }

    public void addInstance(Instance instance) {
        if(columnNames == null) {
            columnNames = new String[instance.getAttributes().length];
            for(int i = 0; i < columnNames.length; i++) {
                columnNames[i] = String.valueOf(i);
            }
        }
        if(columnNames.length != instance.getAttributes().length) {
            throw new IllegalArgumentException("Number of attributes doesn't match dataset columns!");
        }
        instances.add(instance);
    }

    public static Dataset loadFromCsv(String filePath, String delimiter, boolean containsHeader, double prob)
            throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        Dataset dataset = new Dataset();
        String line = null;

        if(containsHeader == true) {
            dataset.columnNames = br.readLine().split(delimiter);
        }
        while((line = br.readLine()) != null) {
            if(Math.random() < prob) {
                dataset.addInstance(new Instance(line.split(delimiter)));
            }
        }

        return dataset;
    }
}
