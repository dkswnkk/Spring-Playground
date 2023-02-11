package practice;

import java.util.Arrays;
import java.util.stream.Stream;

public class Sample03 {
    public static void main(String[] args) {

        Data[] data = {
                new Data(1, "일번"),
                new Data(2, "이번"),
                new Data(3, "삼번")
        };

        Stream<Data> stm = Arrays.stream(data);
        stm.forEach(System.out::println);

        System.out.println("==============");

        Arrays.stream(data)
                .forEach(System.out::print);
    }
}


class Data {
    int num;
    String name;

    public Data(int num, String name) {
        this.num = num;
        this.name = name;
    }

    public int getNum() {
        return num;
    }

    public String getName() {
        return name;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Data{" +
                "num=" + num +
                ", name='" + name + '\'' +
                '}';
    }
}