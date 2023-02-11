package practice;

import java.util.Collections;
import java.util.List;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Sample15 {
    public static void main(String[] args) {
        List<String> locals = Arrays.asList("서울", "대전", "대구", "광주", "울산");
        List<String> reversedLocals = locals.stream()
                .collect(Collectors.collectingAndThen(Collectors.toList(), city -> {
                    Collections.reverse(city);
                    return city.stream();
                }))
                .collect(Collectors.toList());

        System.out.println(locals);
        System.out.println("============================");
        System.out.println(reversedLocals);
        System.out.println("============================");


        List<String> unmodifiedReversedLocals = locals.stream()
                .collect(Collectors.collectingAndThen(Collectors.toList(), city -> {
                    Collections.reverse(city);
                    return city.stream();
                }))
                .collect(Collectors.collectingAndThen(Collectors.toList(), Collections::unmodifiableList));

        //        locals.add("제주"); // 불가
        reversedLocals.add("제주");   // 가능
//        unmodifiedReversedLocals.add("제주"); // 불가

        Collections.reverse(locals);
        System.out.println(locals);
    }
}
