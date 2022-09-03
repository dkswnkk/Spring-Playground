package com.example.junit5;


public class Study {
    private final StudyStatus status = StudyStatus.DRAFT;

    public StudyStatus getStatus() {
        return this.status;
    }
}
