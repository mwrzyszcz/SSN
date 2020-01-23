import java.util.Arrays;

public class Neuron {

  private Network.TypeOfLayer typeOfLayer;
  private double threshold = 0.5 - Math.random();
  private double[] weights = {0.5 - Math.random(), 0.5 - Math.random()};
  private double output = 0;
  private double error = 0.0;

  public Neuron(Network.TypeOfLayer typeOfLayer) {
    this.typeOfLayer = typeOfLayer;
  }

  public void activateSigmoidFunction(double weightedSum) {
    output = 1.0 / (1 + Math.exp(-1.0 * weightedSum));
  }

  public double calculateDerivativeError() {
    return output * (1.0 - output);
  }

  public Network.TypeOfLayer getTypeOfLayer() {
    return typeOfLayer;
  }

  public double getThreshold() {
    return threshold;
  }

  public void setThreshold(double threshold) {
    this.threshold = threshold;
  }

  public double[] getWeights() {
    return weights;
  }

  public double getOutput() {
    return output;
  }

  public void setOutput(double output) {
    this.output = output;
  }

  public double getError() {
    return error;
  }

  public void setError(double error) {
    this.error = error;
  }

  @Override
  public String toString() {
    return "Neuron{" +
        "typeOfLayer=" + typeOfLayer +
        ", threshold=" + threshold +
        ", weights=" + Arrays.toString(weights) +
        ", output=" + output +
        ", error=" + error +
        '}';
  }
}
