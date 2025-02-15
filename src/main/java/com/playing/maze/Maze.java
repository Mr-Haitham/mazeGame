package com.playing.maze;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Maze {
    private int width;
    private int height;
    private Cell[][] grid;
    private Cell startCell;
    private Cell goalCell;

    public Maze(int width, int height) {
        this.width = width;
        this.height = height;
        this.grid = new Cell[width][height];
        generateMaze();
    }

    // Method to set the grid manually
    public void setGrid(Cell[][] grid) {
        this.grid = grid;
        // Update start and goal cells if they exist in the new grid
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (grid[x][y].isStart()) {
                    this.startCell = grid[x][y];
                }
                if (grid[x][y].isGoal()) {
                    this.goalCell = grid[x][y];
                }
            }
        }
    }

    public void generateMaze() {
        Random random = new Random();

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                CellType type = CellType.values()[random.nextInt(CellType.values().length)];
                boolean isStart = (x == 0 && y == 0);
                boolean isGoal = (x == width - 1 && y == height - 1);

                grid[x][y] = new Cell(x, y, type, isStart, isGoal);

                if (isStart) {
                    startCell = grid[x][y];
                }
                if (isGoal) {
                    goalCell = grid[x][y];
                }
            }
        }
    }

    public List<Cell> getNeighbors(Cell cell) {
        List<Cell> neighbors = new ArrayList<>();
        int x = cell.getX();
        int y = cell.getY();

        if (y > 0) {
            neighbors.add(grid[x][y - 1]);
        }
        if (y < height - 1) {
            neighbors.add(grid[x][y + 1]);
        }
        if (x > 0) {
            neighbors.add(grid[x - 1][y]);
        }
        if (x < width - 1) {
            neighbors.add(grid[x + 1][y]);
        }

        return neighbors;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Cell[][] getGrid() {
        return grid;
    }

    public Cell getStartCell() {
        return startCell;
    }

    public Cell getGoalCell() {
        return goalCell;
    }

    public void setStartCell(Cell newStart) {
        if (newStart.getType() != CellType.WALL && newStart.getType() != CellType.WATER) {
            if (startCell != null) {
                startCell.setStart(false); // Reset previous start cell
            }
            newStart.setStart(true);
            startCell = newStart;
        }
    }

    public void setGoalCell(Cell newGoal) {
        if (newGoal.getType() != CellType.WALL && newGoal.getType() != CellType.WATER) {
            if (goalCell != null) {
                goalCell.setGoal(false); // Reset previous goal cell
            }
            newGoal.setGoal(true);
            goalCell = newGoal;
        }
    }
}