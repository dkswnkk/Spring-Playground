package practice;

import java.util.Arrays;
import java.util.stream.Stream;

public class Sample04 {
    public static void main(String[] args) {
        String[] str1 = {"가", "나", "다"};
        String[] str2 = {"A", "B", "C"};

        Stream<String[]> strm1 = Stream.of(str1, str2);

        // 각각 출력
        Stream.of(str1, str2)
                .forEach(x -> System.out.println(Arrays.deepToString(x)));

        System.out.println("===================");

        // 합쳐서 출력
        strm1.flatMap(Arrays::stream)
                .forEach(System.out::println);
    }

}
