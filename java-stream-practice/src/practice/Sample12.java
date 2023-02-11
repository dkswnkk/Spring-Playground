package practice;

import java.util.OptionalInt;
import java.util.stream.IntStream;

public class Sample12 {
    public static void main(String[] args) {
        int sum1 = 0;
        OptionalInt sum2 = OptionalInt.of(0);

        sum1 = IntStream.rangeClosed(7, 10)
                .reduce(6, Integer::sum);

        sum2 = IntStream.rangeClosed(6, 10)
                .reduce(Integer::sum);

    }
}
