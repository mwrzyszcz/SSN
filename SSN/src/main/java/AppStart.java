import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.IntStream;

public class AppStart {

  private static final int STAGE = 1500;
  private static double DATA[][][] =
      new double[][][] {
        {{0, 0}, {0}},
        {{0, 1}, {1}},
        {{1, 0}, {1}},
        {{1, 1}, {0}}
      };

  public static void main(String[] args) throws IOException {

    Network network = new Network();
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    boolean loop = true;
    while (loop) {
      System.out.println(
          "Wpisz:\n wynik - aby zobaczyc stan uczenia,\n ucz - aby uczyc siec,\n exit - aby wyjsc\n ");
      String command = bufferedReader.readLine();
      switch (command) {
        case "wynik":
          double[] result = new double[] {0, 0, 0, 0};
          IntStream.range(0, AppStart.DATA.length)
              .forEach(
                  i -> {
                    result[i] =
                        network
                            .forwardPropagation(AppStart.DATA[i][0])
                            .getNeurons()[Network.INPUT_NEURONS + Network.HIDDEN_NEURONS]
                            .getOutput();
                  });
          showResult(result);
          break;
        case "ucz":
          System.out.println("Uczenie...");
          IntStream.range(0, STAGE)
              .forEach(
                  i -> {
                    System.out.println("Sekwencja: " + i);
                    IntStream.range(0, DATA.length)
                        .forEach(
                            j ->
                                network
                                    .forwardPropagation(AppStart.DATA[j][0])
                                    .backPropagation(AppStart.DATA[j][1][0]));
                  });
          System.out.println("Uczenie zakonczone");
          break;
        case "exit":
          loop = false;
          break;
      }
    }
    System.exit(0);
  }

  static void showResult(double[] result) {
    System.out.println("  Wejscie 1        Wejscie 2     Oczekiwany   Stan    ");
    IntStream.range(0, DATA.length)
        .forEach(
            i -> {
              IntStream.range(0, DATA[0][0].length)
                  .forEach(j -> System.out.print("    " + DATA[i][0][j] + "        "));
              System.out.print(
                  "    " + DATA[i][1][0] + "        " + String.format("%.3f", result[i]) + "  \n");
            });
  }
}
