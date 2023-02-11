package practice;

import java.util.Arrays;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Sample02 {
    public static void main(String[] args) {
        // 배열
        String[] strArray = {"가", "나", "다", "라"};
        int[] intArray = {1, 2, 3};
        double[] doubleArray = {1.1, 2.2, 3.3};

        // 스트림 생성
        Stream<String> strStream = Arrays.stream(strArray);
        IntStream intStream = Arrays.stream(intArray);
        DoubleStream doubleStream = Arrays.stream(doubleArray);

        // 출력
        strStream.forEach(System.out::println);
        intStream.forEach(System.out::println);
        doubleStream.forEach(System.out::println);
    }
}
