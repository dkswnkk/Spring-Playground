package com.example.junit5;


public class Study {
    private final StudyStatus status = StudyStatus.DRAFT;
    private int limit;
    private int age;


    public Study(int limit, int age) {
        this.limit = limit;
        if (age < 19) {
            throw new IllegalArgumentException("나이는 19세 이상이어야 합니다.");
        }
        this.age = age;
    }

    public StudyStatus getStatus() {
        return this.status;
    }

    public int getLimit() {
        return limit;
    }
}
