package com.example.APIPractice.user;

import org.junit.jupiter.api.Assertions;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.constraints.Past;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class Test {


    @org.junit.jupiter.api.Test
    public void test() {
        // given
        class User {
            String name;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getAge() {
                return age;
            }

            public void setAge(int age) {
                this.age = age;
            }

            int age;

            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                User user = (User) o;
                return age == user.age && Objects.equals(name, user.name);
            }

            @Override
            public int hashCode() {
                return Objects.hash(name, age);
            }
        }

        User user1 = new User();
        user1.setName("안주형");
        user1.setAge(25);


        // when
        User user2 = new User();
        BeanUtils.copyProperties(user1, user2);

        //then
        assertEquals(user1, user2);

        System.out.println("user1 name: " + user1.getName());
        System.out.println("user1의 age: " + user1.getAge());
        System.out.println("user2의 name: " + user2.getName());
        System.out.println("user2의 age: " + user2.getAge());
    }
}