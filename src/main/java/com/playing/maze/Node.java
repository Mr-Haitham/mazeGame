package com.playing.maze;

public class Node {
    // The cell this node represents
    private Cell cell;

    // The node from which this node was reached
    private Node parent;

    // Cost from start to this node
    private double gCost;

    // Heuristic cost from this node to the goal
    private double hCost;

    // Total cost: gCost + hCost
    private double fCost;

    // Constructor to initialize the node
    public Node(Cell cell, Node parent, double gCost, double hCost) {
        this.cell = cell;
        this.parent = parent;
        this.gCost = gCost;
        this.hCost = hCost;
        this.fCost = calculateFCost();
    }

    // Method to compute fCost = gCost + hCost
    public double calculateFCost() {
        return gCost + hCost;
    }

    // Getters and setters for the properties
    public Cell getCell() {
        return cell;
    }

    public void setCell(Cell cell) {
        this.cell = cell;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public double getGCost() {
        return gCost;
    }

    public void setGCost(double gCost) {
        this.gCost = gCost;
        this.fCost = calculateFCost(); // Update fCost when gCost changes
    }

    public double getHCost() {
        return hCost;
    }

    public void setHCost(double hCost) {
        this.hCost = hCost;
        this.fCost = calculateFCost(); // Update fCost when hCost changes
    }

    public double getFCost() {
        return fCost;
    }
}

