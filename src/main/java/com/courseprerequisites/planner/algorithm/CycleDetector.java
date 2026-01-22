package com.courseprerequisites.planner.algorithm;

import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class CycleDetector {

    private enum NodeState {
        UNVISITED,
        VISITING,
        VISITED
    }

    public Optional<List<Long>> detectCycle(Map<Long, List<Long>> graph, Long startNode) {
        Map<Long, NodeState> states = new HashMap<>();
        Map<Long, Long> parent = new HashMap<>();

        for (Long node : graph.keySet()) {
            states.put(node, NodeState.UNVISITED);
        }

        List<Long> currentPath = new ArrayList<>();

        if (hasCycleDFS(graph, startNode, states, parent, currentPath)) {
            return Optional.of(currentPath);
        }

        return Optional.empty();
    }

    private boolean hasCycleDFS(Map<Long, List<Long>> graph, Long node,
                                Map<Long, NodeState> states,
                                Map<Long, Long> parent,
                                List<Long> currentPath) {

        states.put(node, NodeState.VISITING);
        currentPath.add(node);

        List<Long> neighbors = graph.getOrDefault(node, new ArrayList<>());

        for (Long neighbor : neighbors) {
            if (states.get(neighbor) == NodeState.VISITING) {
                // Cycle detected - build cycle path
                currentPath.add(neighbor);
                return true;
            }

            if (states.get(neighbor) == NodeState.UNVISITED) {
                parent.put(neighbor, node);
                if (hasCycleDFS(graph, neighbor, states, parent, currentPath)) {
                    return true;
                }
            }
        }

        states.put(node, NodeState.VISITED);
        currentPath.remove(currentPath.size() - 1);

        return false;
    }

    public boolean hasCycle(Map<Long, List<Long>> graph, Long startNode) {
        return detectCycle(graph, startNode).isPresent();
    }
}