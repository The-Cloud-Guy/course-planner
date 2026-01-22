package com.courseprerequisites.planner.service;

import com.courseprerequisites.planner.domain.Course;
import com.courseprerequisites.planner.domain.Prerequisite;
import com.courseprerequisites.planner.domain.dto.CourseOrderResponseDto;
import com.courseprerequisites.planner.domain.dto.CourseRequestDto;
import com.courseprerequisites.planner.domain.dto.CycleDetectionResponseDto;
import com.courseprerequisites.planner.domain.dto.PrerequisiteRequestDto;
import com.courseprerequisites.planner.exception.CourseNotFoundException;
import com.courseprerequisites.planner.exception.InvalidPrerequisiteException;
import com.courseprerequisites.planner.repository.CourseRepository;
import com.courseprerequisites.planner.repository.PrerequisiteRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final PrerequisiteRepository prerequisiteRepository;
    private final GraphService graphService;

    public CourseService(CourseRepository courseRepository,
                         PrerequisiteRepository prerequisiteRepository,
                         GraphService graphService) {
        this.courseRepository = courseRepository;
        this.prerequisiteRepository = prerequisiteRepository;
        this.graphService = graphService;
    }

    @Transactional
    public Course createCourse(CourseRequestDto requestDto) {
        if (courseRepository.existsByCode(requestDto.getCode())) {
            throw new InvalidPrerequisiteException("Course with code " + requestDto.getCode() + " already exists");
        }

        Course course = new Course(
                requestDto.getCode(),
                requestDto.getName(),
                requestDto.getDescription(),
                requestDto.getCredits()
        );

        return courseRepository.save(course);
    }

    public Course getCourseById(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new CourseNotFoundException(id));
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    @Transactional
    public Prerequisite addPrerequisite(PrerequisiteRequestDto requestDto) {
        Long courseId = requestDto.getCourseId();
        Long prerequisiteCourseId = requestDto.getPrerequisiteCourseId();

        if (courseId.equals(prerequisiteCourseId)) {
            throw new InvalidPrerequisiteException("A course cannot be a prerequisite of itself");
        }

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new CourseNotFoundException(courseId));

        Course prerequisiteCourse = courseRepository.findById(prerequisiteCourseId)
                .orElseThrow(() -> new CourseNotFoundException(prerequisiteCourseId));

        if (prerequisiteRepository.existsByCourseIdAndPrerequisiteCourseId(courseId, prerequisiteCourseId)) {
            throw new InvalidPrerequisiteException("This prerequisite relationship already exists");
        }

        graphService.validatePrerequisite(courseId, prerequisiteCourseId);

        Prerequisite prerequisite = new Prerequisite(courseId, prerequisiteCourseId);
        return prerequisiteRepository.save(prerequisite);
    }

    public CycleDetectionResponseDto canCompleteCourse(Long id) {
        Course course = getCourseById(id);

        Optional<List<String>> cycle = graphService.detectCycle(id);

        if (cycle.isPresent()) {
            List<String> cyclePath = cycle.get();
            String message = "Cycle detected: " + String.join(" → ", cyclePath);

            return new CycleDetectionResponseDto(false, true, cyclePath, message);
        }

        return new CycleDetectionResponseDto(true, false, null, "Course can be completed");
    }

    public CourseOrderResponseDto getCourseOrder(Long id) {
        Course course = getCourseById(id);

        List<Long> orderedIds = graphService.getTopologicalOrder(id);

        List<Course> orderedCourses = orderedIds.stream()
                .map(courseId -> courseRepository.findById(courseId).orElse(null))
                .filter(c -> c != null)
                .collect(Collectors.toList());

        return new CourseOrderResponseDto(id, orderedCourses);
    }
}