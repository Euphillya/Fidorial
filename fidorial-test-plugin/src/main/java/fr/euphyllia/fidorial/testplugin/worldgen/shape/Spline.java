package fr.euphyllia.fidorial.testplugin.worldgen.shape;

public final class Spline {

    private final double[] inputs;
    private final double[] outputs;

    private Spline(final double[] inputs, final double[] outputs) {
        this.inputs = inputs;
        this.outputs = outputs;
    }

    public static Spline of(final double[] inputs, final double[] outputs) {
        if (inputs.length != outputs.length || inputs.length < 2) {
            throw new IllegalArgumentException("inputs and outputs must have the same length (>= 2)");
        }
        for (int i = 1; i < inputs.length; i++) {
            if (inputs[i] <= inputs[i - 1]) {
                throw new IllegalArgumentException("inputs must be strictly increasing");
            }
        }
        return new Spline(inputs.clone(), outputs.clone());
    }

    public double apply(final double value) {
        if (value <= inputs[0]) {
            return outputs[0];
        }
        final int last = inputs.length - 1;
        if (value >= inputs[last]) {
            return outputs[last];
        }
        int index = 1;
        while (value > inputs[index]) {
            index++;
        }
        final double lower = inputs[index - 1];
        final double upper = inputs[index];
        final double t = (value - lower) / (upper - lower);
        return outputs[index - 1] + t * (outputs[index] - outputs[index - 1]);
    }
}
