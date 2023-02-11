package practice;

import java.util.Arrays;
import java.util.stream.Stream;

public class Sample06 {
    public static void main(String[] args) {
        String[] arrStr = null;

        // Null을 Stream으로 변환하면 NullPointerException 발생!!
        Stream<String> nullStream = Stream.of(arrStr);

    }
}
