package com.courseprerequisites.planner.repository;

import com.courseprerequisites.planner.domain.Prerequisite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrerequisiteRepository extends JpaRepository<Prerequisite, Long> {

    List<Prerequisite> findByCourseId(Long courseId);

    List<Prerequisite> findByPrerequisiteCourseId(Long prerequisiteCourseId);

    boolean existsByCourseIdAndPrerequisiteCourseId(Long courseId, Long prerequisiteCourseId);

    @Query("SELECT p FROM Prerequisite p")
    List<Prerequisite> findAllPrerequisites();
}