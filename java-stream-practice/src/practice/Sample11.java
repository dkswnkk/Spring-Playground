package practice;

import java.util.stream.IntStream;

public class Sample11 {
    public static void main(String[] args) {
        //1부터 100까지 합
        int sum1 = IntStream.rangeClosed(1, 100)
                .reduce(0, (x, y) -> x + y);

        int sum2 = IntStream.rangeClosed(1, 100)
                .reduce(0, (x, y) -> {
                    x += 2;
                    return x + y;
                });

        int sum3 = IntStream.rangeClosed(1, 100)
                .reduce(0, Integer::sum);


    }
}
