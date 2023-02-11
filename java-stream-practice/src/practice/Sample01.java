package practice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class Sample01 {
    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(1, 2, 3, 2, 1, 5);
        System.out.println(list);
        System.out.println("=====================");

        ArrayList list2 = new ArrayList(new HashSet<Integer>(list));
        list2.forEach(System.out::println);
        System.out.println("=====================");

        list.stream()
                .distinct()
                .forEach(System.out::println);
    }
}
