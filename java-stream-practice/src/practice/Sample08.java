package practice;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Sample08 {
    public static void main(String[] args) {
        List<DataObj> datas = new ArrayList<>();
        IntStream.rangeClosed(1, 100)
                .forEach(i -> {
                    datas.add(
                            new DataObj(1, "이름")
                    );
                });

        datas.forEach(System.out::println);
    }
}

class DataObj {
    int num;
    String name;

    public DataObj(int num, String name) {
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
        return "DataObj{" +
                "num=" + num +
                ", name='" + name + '\'' +
                '}';
    }
}
