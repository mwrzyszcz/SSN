import java.util.stream.IntStream;

public class Network {

  private double LEARN_FREQUENCY = 0.8;
  static final int INPUT_NEURONS = 2;
  static final int HIDDEN_NEURONS = 2;
  private Neuron[] neurons = new Neuron[INPUT_NEURONS + HIDDEN_NEURONS + 1];

  enum TypeOfLayer {
    INPUT,
    HIDDEN_LAYER,
    OUTPUT
  };

  public Neuron[] getNeurons() {
    return neurons;
  }

  public Network() {
    IntStream.range(0, INPUT_NEURONS).forEach(i -> neurons[i] = new Neuron(TypeOfLayer.INPUT));
    IntStream.range(INPUT_NEURONS, INPUT_NEURONS + HIDDEN_NEURONS)
        .forEach(i -> neurons[i] = new Neuron(TypeOfLayer.HIDDEN_LAYER));
    neurons[INPUT_NEURONS + HIDDEN_NEURONS] = new Neuron(TypeOfLayer.OUTPUT);
  }

  public Network forwardPropagation(double input[]) {
    double weightedSum = 0;
    for (int i = 0; i < neurons.length; i++) {
      switch (neurons[i].getTypeOfLayer()) {
        case INPUT:
          neurons[i].setOutput(input[i]);
          break;
        case HIDDEN_LAYER:
          weightedSum =
              neurons[i].getThreshold()
                  + neurons[i].getWeights()[0] * neurons[0].getOutput()
                  + neurons[i].getWeights()[1] * neurons[1].getOutput();
          neurons[i].thresholdActivation(weightedSum);
          break;
        case OUTPUT:
          weightedSum =
              neurons[i].getThreshold()
                  + neurons[i].getWeights()[0] * neurons[2].getOutput()
                  + neurons[i].getWeights()[1] * neurons[3].getOutput();
          neurons[i].thresholdActivation(weightedSum);
          break;
      }
    }
    return this;
  }

  public Network backPropagation(double targetXOR) {
    for (int i = 0; i < neurons.length; i++) {
      neurons[2].setError(
          (neurons[4].getWeights()[0] * neurons[4].getError())
              * neurons[2].calculateDerivativeError());
      neurons[2].setThreshold(neurons[2].getThreshold() + LEARN_FREQUENCY * neurons[2].getError());
      neurons[2].getWeights()[0] =
          neurons[2].getWeights()[0]
              + LEARN_FREQUENCY * neurons[2].getError() * neurons[0].getOutput();
      neurons[2].getWeights()[1] =
          neurons[2].getWeights()[1]
              + LEARN_FREQUENCY * neurons[2].getError() * neurons[1].getOutput();
      neurons[3].setError(
          (neurons[4].getWeights()[1] * neurons[4].getError())
              * neurons[3].calculateDerivativeError());
      neurons[3].setThreshold(neurons[3].getThreshold() + LEARN_FREQUENCY * neurons[3].getError());
      neurons[3].getWeights()[0] =
          neurons[3].getWeights()[0]
              + LEARN_FREQUENCY * neurons[3].getError() * neurons[0].getOutput();
      neurons[3].getWeights()[1] =
          neurons[3].getWeights()[1]
              + LEARN_FREQUENCY * neurons[3].getError() * neurons[1].getOutput();
      neurons[4].setError(
          (targetXOR - neurons[4].getOutput()) * neurons[4].calculateDerivativeError());
      neurons[4].setThreshold(neurons[4].getThreshold() + LEARN_FREQUENCY * neurons[4].getError());
      neurons[4].getWeights()[0] =
          neurons[4].getWeights()[0]
              + LEARN_FREQUENCY * neurons[4].getError() * neurons[2].getOutput();
      neurons[4].getWeights()[1] =
          neurons[4].getWeights()[1]
              + LEARN_FREQUENCY * neurons[4].getError() * neurons[3].getOutput();
    }
    return this;
  }
}
