import java.util.stream.IntStream;

// zaimplementuj warunki stopu: maksymalną liczbę iteracji, maksymalny dopuszczalny błąd dla epoki,
// dane startowe, dane związane z pojedynczym krokiem, podsumowanie procesu uczenia,

public class AppStart {

  // przy 100 ITERATION oraz 5 EPOCH najbliższy rezultat, ponad występuje już przetrenowanie i sieć
  // zaczyna wariować
  private static final int ITERATION = 10;
  private static final int EPOCH = 5;
  private static double[][][] NXOR =
      new double[][][] {
        {{1, 1}, {1}},
        {{1, 0}, {0}},
        {{0, 1}, {0}},
        {{0, 0}, {1}}
      };

  public static void main(String[] args) {

    Network network = new Network();
    for (int i = 0; i < EPOCH; i++) {
      System.out.println("Uczenie...");
      IntStream.range(0, ITERATION)
          .forEach(
              f -> {
                System.out.println("Iteracja: " + f);
                IntStream.range(0, NXOR.length)               //TODO: "program wyświetla: dane związane z pojedynczym krokiem"
                    .forEach(
                        j -> network.forwardPropagation(NXOR[j][0]).backPropagation(NXOR[j][1][0]));
              });
      System.out.println("Zakończono epokę: " + (i + 1));
      printAfterTrainResult(network);
    }
  }

  private static void printAfterTrainResult(Network network) {
    double[] result = new double[] {0, 0, 0, 0};
    IntStream.range(0, AppStart.NXOR.length)
        .forEach(
            i -> {
              result[i] =
                  network
                      .forwardPropagation(AppStart.NXOR[i][0])
                      .getNeurons()[Network.INPUT_NEURONS + Network.HIDDEN_NEURONS]
                      .getOutput();
            });

    showResult(result);
  }

  private static void showResult(double[] result) {
    System.out.println("  Wejscie 1        Wejscie 2     Oczekiwany   Stan    ");
    IntStream.range(0, NXOR.length)
        .forEach(
            i -> {
              IntStream.range(0, NXOR[0][0].length)
                  .forEach(j -> System.out.print("    " + NXOR[i][0][j] + "        "));
              System.out.print(
                  "    " + NXOR[i][1][0] + "        " + String.format("%.3f", result[i]) + "  \n");
            });
  }
}
