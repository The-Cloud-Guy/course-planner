package com.courseprerequisites.planner.controller;

import com.courseprerequisites.planner.domain.Course;
import com.courseprerequisites.planner.domain.Prerequisite;
import com.courseprerequisites.planner.domain.dto.CourseOrderResponseDto;
import com.courseprerequisites.planner.domain.dto.CourseRequestDto;
import com.courseprerequisites.planner.domain.dto.CycleDetectionResponseDto;
import com.courseprerequisites.planner.domain.dto.PrerequisiteRequestDto;
import com.courseprerequisites.planner.service.CourseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/courses")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping
    public ResponseEntity<Course> createCourse(@RequestBody CourseRequestDto requestDto) {
        Course course = courseService.createCourse(requestDto);
        return new ResponseEntity<>(course, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Course>> getAllCourses() {
        List<Course> courses = courseService.getAllCourses();
        return ResponseEntity.ok(courses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourseById(@PathVariable Long id) {
        Course course = courseService.getCourseById(id);
        return ResponseEntity.ok(course);
    }

    @PostMapping("/prerequisite")
    public ResponseEntity<Prerequisite> addPrerequisite(@RequestBody PrerequisiteRequestDto requestDto) {
        Prerequisite prerequisite = courseService.addPrerequisite(requestDto);
        return new ResponseEntity<>(prerequisite, HttpStatus.CREATED);
    }

    @GetMapping("/{id}/can-complete")
    public ResponseEntity<CycleDetectionResponseDto> canCompleteCourse(@PathVariable Long id) {
        CycleDetectionResponseDto response = courseService.canCompleteCourse(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/order")
    public ResponseEntity<CourseOrderResponseDto> getCourseOrder(@PathVariable Long id) {
        CourseOrderResponseDto response = courseService.getCourseOrder(id);
        return ResponseEntity.ok(response);
    }
}