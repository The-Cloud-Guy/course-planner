package com.courseprerequisites.planner.domain.dto;

import com.courseprerequisites.planner.domain.Course;

import java.util.List;

public class CourseOrderResponseDto {

    private Long courseId;
    private List<Course> orderedCourses;
    private Integer totalCourses;

    public CourseOrderResponseDto() {}

    public CourseOrderResponseDto(Long courseId, List<Course> orderedCourses) {
        this.courseId = courseId;
        this.orderedCourses = orderedCourses;
        this.totalCourses = orderedCourses != null ? orderedCourses.size() : 0;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public List<Course> getOrderedCourses() {
        return orderedCourses;
    }

    public void setOrderedCourses(List<Course> orderedCourses) {
        this.orderedCourses = orderedCourses;
        this.totalCourses = orderedCourses != null ? orderedCourses.size() : 0;
    }

    public Integer getTotalCourses() {
        return totalCourses;
    }

    public void setTotalCourses(Integer totalCourses) {
        this.totalCourses = totalCourses;
    }
}
