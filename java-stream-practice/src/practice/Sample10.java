package practice;

import java.util.stream.IntStream;

public class Sample10 {
    public static void main(String[] args) {
        int sum1 = 0;
        int sum2 = 0;

        for (int i = 6; i <= 10; i++) {
            sum1 += i;
        }

        sum2 = IntStream.rangeClosed(7, 10)
                .reduce(6, Integer::sum);

        System.out.println(sum2);
    }

}
