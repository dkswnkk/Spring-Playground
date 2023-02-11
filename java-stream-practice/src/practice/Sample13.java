package practice;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Sample13 {
    public static void main(String[] args) {
        String[] animals = {"가", "가", "나", "다", "라", "다"};

        // 기본 배열 출력
        System.out.println(Arrays.toString(animals));

        // List로 변환 후 출력
        List<String> animals1 = Arrays.stream(animals)
                .collect(Collectors.toList());
        System.out.println(animals1);

        // Set으로 변환 후 출력
        Set<String> animals2 = Arrays.stream(animals)
                .collect(Collectors.toSet());
        System.out.println(animals2);

        // TreeSet으로 변환 후 출력
        Set<String> animals3 = Arrays.stream(animals)
                .collect(Collectors.toCollection(TreeSet::new));
        System.out.println(animals3);

        // HashMap으로 변환 후 출력
        Map<String, Integer> animals5 = Arrays.stream(animals)
                .collect(Collectors.toMap(Function.identity(), String::length, (x1, x2) -> x1 + x2));
        System.out.println(animals5);

    }
}
