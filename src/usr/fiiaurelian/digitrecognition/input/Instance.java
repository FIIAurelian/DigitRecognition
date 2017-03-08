package usr.fiiaurelian.digitrecognition.input;

import java.util.List;
import java.util.ArrayList;

/**
 * Created by aurelian on 08.03.2017.
 */
public class Instance {

    private int[] attributes;

    public Instance(int[] attributes) {
        this.attributes = attributes;
    }

    public Instance(String[] attributes) {
        this.attributes = new int[attributes.length];
        for(int i = 0; i < attributes.length; i++) {
            this.attributes[i] = Integer.parseInt(attributes[i]);
        }
    }

    public int[] getAttributes() {
        return attributes;
    }

    /*public static final class Builder {

        private static List<String> attributes = new ArrayList<>();

        public Builder withAttribute(String attribute) {
            this.attributes.add(attribute);
            return this;
        }

        public Instance build() {
            System.out.println("Build instance with " + attributes.size() + " attributes.");
            Instance instance = new Instance(this);
            attributes.clear();
            return instance;
        }
    }*/
}
