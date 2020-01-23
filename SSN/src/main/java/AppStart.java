import java.util.stream.IntStream;

// zaimplementuj warunki stopu: maksymalną liczbę iteracji, maksymalny dopuszczalny błąd dla epoki,
// dane startowe, dane związane z pojedynczym krokiem, podsumowanie procesu uczenia,

public class AppStart {

    // przy 100 ITERATION oraz 5 EPOCH najbliższy rezultat, ponad występuje już przetrenowanie i sieć
    // zaczyna wariować
    private static final int ITERATION = 100;
    private static final int EPOCH = 2000;
    private static double[][][] NXOR =
            new double[][][]{
                    {{1, 1}, {1}},
                    {{1, 0}, {0}},
                    {{0, 1}, {0}},
                    {{0, 0}, {1}}
            };

    public static void main(String[] args) {

        Network network = new Network();
        for (int i = 0; i < EPOCH; i++) {
            System.out.println("Uczenie...");
            for (int j = 0; j < ITERATION; j++) {
                System.out.println("Iteracja: " + j);
                for (int k = 0; k < NXOR.length; k++) {
                    network.forwardPropagation(NXOR[k][0]).backPropagation(NXOR[k][1][0]);
                    printAfterTrainResult(network);
                }
            }
            System.out.println("----------------------Zakończono epokę: " + (i + 1) + "----------------------");
//            printAfterTrainResult(network);
        }
        System.out.println("Sieć wytrenowana finalny rezultat");
        printAfterTrainResult(network);
    }

    private static void printAfterTrainResult(Network network) {
        double[] outputResultArray = new double[]{0, 0, 0, 0};
        for (int i = 0; i < AppStart.NXOR.length; i++) {
            outputResultArray[i] = network
                    .forwardPropagation(AppStart.NXOR[i][0])
                    .getNeurons()[Network.INPUT_NEURONS + Network.HIDDEN_NEURONS]
                    .getOutput();
        }
        showEpoch(outputResultArray);
    }

    //pętla zewnętrzna jest odpowiedzialna za wyświetlenie wejscia 1,2 orz oczekiwany, zewnętrzna wyświetla wynik uzyskany na podstawie parametrów wejściowych
    private static void showEpoch(double[] result) {
        System.out.println("  Wejscie 1        Wejscie 2     Oczekiwany   Stan    ");
        for (int i = 0; i < NXOR.length; i++) {
            for (int j = 0; j < NXOR[0][0].length; j++) {
                System.out.print("    " + NXOR[i][0][j] + "        ");
            }
            System.out.print("    " + NXOR[i][1][0] + "        " + String.format("%.3f", result[i]) + "  \n");
        }
    }
}
