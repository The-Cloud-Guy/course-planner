package com.courseprerequisites.planner.algorithm;

import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class TopologicalSorter {

    private enum NodeState {
        UNVISITED,
        VISITING,
        VISITED
    }

    public List<Long> sort(Map<Long, List<Long>> graph, Long startNode) {
        Map<Long, NodeState> states = new HashMap<>();
        Stack<Long> result = new Stack<>();

        for (Long node : graph.keySet()) {
            states.put(node, NodeState.UNVISITED);
        }

        if (!states.containsKey(startNode)) {
            states.put(startNode, NodeState.UNVISITED);
        }

        if (topologicalSortDFS(graph, startNode, states, result)) {
            throw new RuntimeException("Cycle detected during topological sort");
        }

        List<Long> orderedList = new ArrayList<>();
        while (!result.isEmpty()) {
            orderedList.add(result.pop());
        }

        return orderedList;
    }

    private boolean topologicalSortDFS(Map<Long, List<Long>> graph, Long node,
                                       Map<Long, NodeState> states,
                                       Stack<Long> result) {

        states.put(node, NodeState.VISITING);

        List<Long> neighbors = graph.getOrDefault(node, new ArrayList<>());

        for (Long neighbor : neighbors) {
            if (!states.containsKey(neighbor)) {
                states.put(neighbor, NodeState.UNVISITED);
            }

            if (states.get(neighbor) == NodeState.VISITING) {
                return true; // Cycle detected
            }

            if (states.get(neighbor) == NodeState.UNVISITED) {
                if (topologicalSortDFS(graph, neighbor, states, result)) {
                    return true;
                }
            }
        }

        states.put(node, NodeState.VISITED);
        result.push(node);

        return false;
    }
}
