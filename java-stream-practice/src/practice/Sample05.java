package practice;

import java.util.ArrayList;
import java.util.stream.Stream;

public class Sample05 {
    public static void main(String[] args) {
        // 배열 생성
        ArrayList<String> list1 = new ArrayList<>();
        list1.add("A");
        list1.add("A");
        list1.add("B");
        list1.add("C");

        ArrayList<Integer> list2 = new ArrayList<>();
        list2.add(1);
        list2.add(1);
        list2.add(2);
        list2.add(3);
        list2.add(3);
        list2.add(3);

        // 스트팀 생성
        Stream<String> arrStrm1 = list1.stream();
        Stream<Integer> arrStrm2 = list2.stream();

        // 스트림을 이용한 출력
        arrStrm1.forEach(System.out::println);
        System.out.println("================");
        arrStrm2.forEach(System.out::println);
        System.out.println("================");

        // 컬렉션에서 스트림으로 바로 출력
        list1.stream()
                .distinct()
                .forEach(System.out::println);
        System.out.println("================");
        list2.stream()
                .distinct()
                .forEach(System.out::println);
    }
}
