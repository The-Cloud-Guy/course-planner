package com.courseprerequisites.planner.domain.dto;

public class PrerequisiteRequestDto {

    private Long courseId;
    private Long prerequisiteCourseId;

    public PrerequisiteRequestDto() {}

    public PrerequisiteRequestDto(Long courseId, Long prerequisiteCourseId) {
        this.courseId = courseId;
        this.prerequisiteCourseId = prerequisiteCourseId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public Long getPrerequisiteCourseId() {
        return prerequisiteCourseId;
    }

    public void setPrerequisiteCourseId(Long prerequisiteCourseId) {
        this.prerequisiteCourseId = prerequisiteCourseId;
    }
}
