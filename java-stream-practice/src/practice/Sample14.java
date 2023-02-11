package practice;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class Sample14 {
    public static void main(String[] args) {
        String[][] level = {{"가", "1"}, {"나", "2"}, {"다", "3"}, {"다", "3"}};

        Map<Object, Object> map = Arrays.stream(level)
                .collect(Collectors.toMap(x -> x[0], x -> x[1], (x1, x2) -> x1));
        System.out.println(map);
    }
}
