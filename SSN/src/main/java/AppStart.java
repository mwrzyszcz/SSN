import java.io.IOException;
import java.util.stream.IntStream;

//zaimplementuj warunki stopu: maksymalną liczbę iteracji, maksymalny dopuszczalny błąd dla epoki,
//dane startowe, dane związane z pojedynczym krokiem, podsumowanie procesu uczenia,

public class AppStart {

    //przy 100 ITERATION oraz 5 EPOCH najbliższy rezultat, ponad występuje już przetrenowanie i sieć zaczyna wariować
    private static final int ITERATION = 100;
    private static final int EPOCH = 5;
    private static double DATA[][][] =
            new double[][][]{
                    {{1, 1}, {1}},
                    {{1, 0}, {0}},
                    {{0, 1}, {0}},
                    {{0, 0}, {1}}
            };

    public static void main(String[] args) throws IOException, InterruptedException {

        Network network = new Network();
        for (int i = 0; i < EPOCH; i++) {
            System.out.println("Uczenie...");
            IntStream.range(0, ITERATION)
                    .forEach(
                            f -> {
                                System.out.println("Sekwencja: " + f);
                                IntStream.range(0, DATA.length)
                                        .forEach(
                                                j ->
                                                        network
                                                                .forwardPropagation(AppStart.DATA[j][0])
                                                                .backPropagation(AppStart.DATA[j][1][0]));
                            });
            System.out.println("Zakończono epokę: " + (i+1));
            printAfterTrainResult(network);
        }

    }

    private static void printAfterTrainResult(Network network) {
        double[] result = new double[]{0, 0, 0, 0};
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
