package com.courseprerequisites.planner.domain.dto;

import java.util.List;

public class CycleDetectionResponseDto {

    private boolean canComplete;
    private boolean hasCycle;
    private List<String> cyclePath;
    private String message;

    public CycleDetectionResponseDto() {}

    public CycleDetectionResponseDto(boolean canComplete, boolean hasCycle, List<String> cyclePath, String message) {
        this.canComplete = canComplete;
        this.hasCycle = hasCycle;
        this.cyclePath = cyclePath;
        this.message = message;
    }

    public boolean isCanComplete() {
        return canComplete;
    }

    public void setCanComplete(boolean canComplete) {
        this.canComplete = canComplete;
    }

    public boolean isHasCycle() {
        return hasCycle;
    }

    public void setHasCycle(boolean hasCycle) {
        this.hasCycle = hasCycle;
    }

    public List<String> getCyclePath() {
        return cyclePath;
    }

    public void setCyclePath(List<String> cyclePath) {
        this.cyclePath = cyclePath;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
