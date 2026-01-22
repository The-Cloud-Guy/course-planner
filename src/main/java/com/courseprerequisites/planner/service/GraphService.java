package com.courseprerequisites.planner.service;

import com.courseprerequisites.planner.algorithm.CycleDetector;
import com.courseprerequisites.planner.algorithm.TopologicalSorter;
import com.courseprerequisites.planner.domain.Course;
import com.courseprerequisites.planner.domain.Prerequisite;
import com.courseprerequisites.planner.exception.CycleDetectedException;
import com.courseprerequisites.planner.repository.CourseRepository;
import com.courseprerequisites.planner.repository.PrerequisiteRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class GraphService {

    private final PrerequisiteRepository prerequisiteRepository;
    private final CourseRepository courseRepository;
    private final CycleDetector cycleDetector;
    private final TopologicalSorter topologicalSorter;

    public GraphService(PrerequisiteRepository prerequisiteRepository,
                        CourseRepository courseRepository,
                        CycleDetector cycleDetector,
                        TopologicalSorter topologicalSorter) {
        this.prerequisiteRepository = prerequisiteRepository;
        this.courseRepository = courseRepository;
        this.cycleDetector = cycleDetector;
        this.topologicalSorter = topologicalSorter;
    }

    public Map<Long, List<Long>> buildGraph() {
        List<Prerequisite> prerequisites = prerequisiteRepository.findAllPrerequisites();
        Map<Long, List<Long>> graph = new HashMap<>();

        for (Prerequisite prerequisite : prerequisites) {
            graph.computeIfAbsent(prerequisite.getCourseId(), k -> new ArrayList<>())
                    .add(prerequisite.getPrerequisiteCourseId());
        }

        return graph;
    }

    public Map<Long, List<Long>> buildGraphForCourse(Long courseId) {
        Map<Long, List<Long>> graph = buildGraph();
        Map<Long, List<Long>> subgraph = new HashMap<>();
        Set<Long> visited = new HashSet<>();

        buildSubgraphDFS(courseId, graph, subgraph, visited);

        return subgraph;
    }

    private void buildSubgraphDFS(Long node, Map<Long, List<Long>> fullGraph,
                                  Map<Long, List<Long>> subgraph, Set<Long> visited) {
        if (visited.contains(node)) {
            return;
        }

        visited.add(node);
        List<Long> neighbors = fullGraph.getOrDefault(node, new ArrayList<>());

        if (!neighbors.isEmpty()) {
            subgraph.put(node, new ArrayList<>(neighbors));

            for (Long neighbor : neighbors) {
                buildSubgraphDFS(neighbor, fullGraph, subgraph, visited);
            }
        }
    }

    public void validatePrerequisite(Long courseId, Long prerequisiteCourseId) {
        Map<Long, List<Long>> graph = buildGraph();

        graph.computeIfAbsent(courseId, k -> new ArrayList<>()).add(prerequisiteCourseId);

        Optional<List<Long>> cycle = cycleDetector.detectCycle(graph, courseId);

        if (cycle.isPresent()) {
            List<String> cyclePath = cycle.get().stream()
                    .map(id -> courseRepository.findById(id)
                            .map(Course::getCode)
                            .orElse(id.toString()))
                    .collect(Collectors.toList());

            String message = "Adding this prerequisite would create a cycle: " +
                    String.join(" → ", cyclePath);

            throw new CycleDetectedException(message, cyclePath);
        }
    }

    public Optional<List<String>> detectCycle(Long courseId) {
        Map<Long, List<Long>> graph = buildGraphForCourse(courseId);

        if (graph.isEmpty()) {
            return Optional.empty();
        }

        Optional<List<Long>> cycle = cycleDetector.detectCycle(graph, courseId);

        if (cycle.isPresent()) {
            List<String> cyclePath = cycle.get().stream()
                    .map(id -> courseRepository.findById(id)
                            .map(Course::getCode)
                            .orElse(id.toString()))
                    .collect(Collectors.toList());

            return Optional.of(cyclePath);
        }

        return Optional.empty();
    }

    public List<Long> getTopologicalOrder(Long courseId) {
        Map<Long, List<Long>> graph = buildGraphForCourse(courseId);

        if (graph.isEmpty()) {
            return Collections.singletonList(courseId);
        }

        return topologicalSorter.sort(graph, courseId);
    }
}
