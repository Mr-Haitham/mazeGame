package com.playing.maze;

import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.ArrayList;
import java.util.List;

public class AStar {
    private PriorityQueue<Node> openSet;
    private Set<Node> closedSet;
    private Maze maze;

    // Constructor to initialize the A* algorithm with a maze
    public AStar(Maze maze) {
        this.maze = maze;
        this.openSet = new PriorityQueue<>(Comparator.comparingDouble(Node::getFCost));
        this.closedSet = new HashSet<>();
    }

    public List<Cell> findPath() {
        // Initialize openSet with the start node
        Cell startCell = maze.getStartCell();
        Node startNode = new Node(startCell, null, 0, calculateHeuristic(startCell, maze.getGoalCell()));
        openSet.add(startNode);

        while (!openSet.isEmpty()) {
            // Get the node with the lowest fCost from openSet
            Node current = openSet.poll();

            // If this node is the goal, reconstruct the path and return it
            if (current.getCell().isGoal()) {
                List<Cell> path = reconstructPath(current);
                System.out.println("Path found: " + path.size() + " steps."); // Debug statement
                return path;
            }

            // Move current node to closedSet
            closedSet.add(current);

            // Evaluate neighbors
            for (Cell neighborCell : maze.getNeighbors(current.getCell())) {
                if (neighborCell.getType() == CellType.WALL || closedSet.contains(new Node(neighborCell, null, 0, 0))) {
                    continue; // Skip if impassable or in closedSet
                }

                double tentativeGCost = current.getGCost() + neighborCell.getCost();

                Node neighborNode = new Node(neighborCell, current, tentativeGCost, calculateHeuristic(neighborCell, maze.getGoalCell()));

                if (!openSet.contains(neighborNode) || tentativeGCost < neighborNode.getGCost()) {
                    neighborNode.setGCost(tentativeGCost);
                    neighborNode.setHCost(calculateHeuristic(neighborCell, maze.getGoalCell()));
                    if (!openSet.contains(neighborNode)) {
                        openSet.add(neighborNode);
                    }
                }
            }
        }

        // If no path is found, return an empty list
        return new ArrayList<>();
    }
    // Method to find all possible paths using DFS
    public List<List<Cell>> findAllPaths(int startX, int startY) {
        List<List<Cell>> allPaths = new ArrayList<>();
        Cell startCell = maze.getGrid()[startX][startY];
        List<Cell> currentPath = new ArrayList<>();
        dfs(startCell, currentPath, allPaths);
        return allPaths;
    }
    // Helper method for DFS
    private void dfs(Cell current, List<Cell> currentPath, List<List<Cell>> allPaths) {
        // Add the current cell to the current path
        currentPath.add(current);

        // If the current cell is the goal, add the current path to allPaths
        if (current.isGoal()) {
            allPaths.add(new ArrayList<>(currentPath));
        } else {
            // Explore neighbors
            for (Cell neighbor : maze.getNeighbors(current)) {
                // Avoid walls and cells already in the current path
                if (neighbor.getType() != CellType.WALL && !currentPath.contains(neighbor)) {
                    dfs(neighbor, currentPath, allPaths);
                }
            }
        }

        // Backtrack: Remove the current cell from the current path
        currentPath.remove(currentPath.size() - 1);
    }

    // Method to reconstruct the path from the goal node to the start node
    private List<Cell> reconstructPath(Node node) {
        List<Cell> path = new ArrayList<>();
        Node current = node;

        // Traverse from the goal node back to the start node using parent references
        while (current != null) {
            path.add(current.getCell()); // Add the current cell to the path
            current = current.getParent(); // Move to the parent node
        }

        // Reverse the path to start from the beginning
        List<Cell> reversedPath = new ArrayList<>();
        for (int i = path.size() - 1; i >= 0; i--) {
            reversedPath.add(path.get(i));
        }

        return reversedPath;
    }

    // Method to calculate the heuristic cost (Manhattan distance)
    private double calculateHeuristic(Cell a, Cell b) {
        return Math.abs(a.getX() - b.getX()) + Math.abs(a.getY() - b.getY());
    }
}