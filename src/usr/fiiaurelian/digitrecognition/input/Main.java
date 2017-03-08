package usr.fiiaurelian.digitrecognition.input;

import java.io.IOException;

/**
 * Created by aurelian on 08.03.2017.
 */
public class Main {

    public static void main(String[] args) throws IOException {
        Dataset dataset = Dataset.loadFromCsv("train.csv", ",", true, 0.3);
        System.out.println("Size: " + dataset.getInstances().size());
    }
}
