package com.courseprerequisites.planner.exception;

import java.util.List;

public class CycleDetectedException extends RuntimeException {

    private List<String> cyclePath;

    public CycleDetectedException(String message) {
        super(message);
    }

    public CycleDetectedException(String message, List<String> cyclePath) {
        super(message);
        this.cyclePath = cyclePath;
    }

    public List<String> getCyclePath() {
        return cyclePath;
    }

    public void setCyclePath(List<String> cyclePath) {
        this.cyclePath = cyclePath;
    }
}
