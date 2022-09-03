package com.example.junit5;


public class Study {
    private final StudyStatus status = StudyStatus.DRAFT;
    private int limit;

    public Study(int limit) {
        this.limit = limit;
    }

    public StudyStatus getStatus() {
        return this.status;
    }

    public int getLimit() {
        return limit;
    }
}
