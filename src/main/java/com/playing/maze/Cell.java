package com.playing.maze;

public class Cell {
    // Coordinates of the cell
    private int x;
    private int y;


    // Terrain type of the cell
    private CellType type;

    // Indicates if this is the starting cell
    private boolean isStart;

    // Indicates if this is the goal cell
    private boolean isGoal;

    // Constructor to initialize the cell
    public Cell(int x, int y, CellType type, boolean isStart, boolean isGoal) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.isStart = isStart;
        this.isGoal = isGoal;
    }

    // Getters and setters for the properties
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public CellType getType() {
        return type;
    }

    public void setType(CellType type) {
        this.type = type;
    }

    public boolean isStart() {
        return isStart;
    }

    public void setStart(boolean isStart) {
        this.isStart = isStart;
    }

    public boolean isGoal() {
        return isGoal;
    }

    public void setGoal(boolean isGoal) {
        this.isGoal = isGoal;
    }

    // Method to get the movement cost based on the terrain type
    public int getCost() {
        switch (this.type) {
            case GRASS:
                return 2;
            case SAND:
                return 3;
            case WATER:
                return 4;
            case WALL:
                // WALL is impassable, so return a high cost or handle it as needed
                return Integer.MAX_VALUE;
            case EMPTY:
            default:
                // EMPTY can be considered as having no cost or minimal cost
                return 1;
        }
    }
}

