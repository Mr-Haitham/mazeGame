package com.playing.maze;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.animation.AnimationTimer;
import javafx.scene.paint.Color;

import java.util.List;
import java.util.Random;

public class MazeController {
    private static final int CELL_SIZE = 60; // Size of each cell
    private static final int MAZE_WIDTH = 10; // Width of the maze in cells
    private static final int MAZE_HEIGHT = 9; // Height of the maze in cells
    private static final Random RANDOM = new Random();

    @FXML
    private Canvas canvas;

    @FXML
    private Pane canvasPane;

    private Cell[][] maze; // 2D array to represent the maze
    private int robotX; // Robot's X position
    private int robotY; // Robot's Y position

    @FXML
    public void initialize() {
        generateRandomMaze();
        drawMaze();
    }

    private void generateRandomMaze() {
        maze = new Cell[MAZE_WIDTH][MAZE_HEIGHT];

        for (int x = 0; x < MAZE_WIDTH; x++) {
            for (int y = 0; y < MAZE_HEIGHT; y++) {
                // Randomly assign cell types
                CellType type;
                if (RANDOM.nextDouble() < 0.2) {
                    type = CellType.WALL; // 20% chance of being a wall
                } else {
                    // Randomly assign other types for empty cells
                    double rand = RANDOM.nextDouble();
                    if (rand < 0.5) {
                        type = CellType.EMPTY; // 50% chance of being empty
                    } else if (rand < 0.75) {
                        type = CellType.GRASS; // 25% chance of being grass
                    } else {
                        type = CellType.WATER; // 25% chance of being water
                    }
                }

                // Create a new Cell object
                Cell cell = new Cell(x, y, type, false, false);
                maze[x][y] = cell;
            }
        }

        // Set one random cell as the target (goal)
        int targetX = RANDOM.nextInt(MAZE_WIDTH);
        int targetY = RANDOM.nextInt(MAZE_HEIGHT);
        maze[targetX][targetY].setType(CellType.GOAL);
        maze[targetX][targetY].setGoal(true);

        // Ensure the target cell is not a wall
        while (maze[targetX][targetY].getType() == CellType.WALL) {
            targetX = RANDOM.nextInt(MAZE_WIDTH);
            targetY = RANDOM.nextInt(MAZE_HEIGHT);
            maze[targetX][targetY].setType(CellType.GOAL);
            maze[targetX][targetY].setGoal(true);
        }

        // Place the robot in a random empty cell
        do {
            robotX = RANDOM.nextInt(MAZE_WIDTH);
            robotY = RANDOM.nextInt(MAZE_HEIGHT);
        } while (maze[robotX][robotY].getType() != CellType.EMPTY);
    }

    private void drawMaze() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight()); // Clear the canvas

        for (int x = 0; x < MAZE_WIDTH; x++) {
            for (int y = 0; y < MAZE_HEIGHT; y++) {
                Cell cell = maze[x][y];
                switch (cell.getType()) {
                    case WALL:
                        gc.setFill(Color.BLACK);
                        break;
                    case EMPTY:
                        gc.setFill(Color.WHITE);
                        break;
                    case GOAL:
                        gc.setFill(Color.RED);
                        break;
                    case WATER:
                        gc.setFill(Color.BLUE);
                        break;
                    case GRASS:
                        gc.setFill(Color.GREEN);
                        break;
                }
                gc.fillRect(x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                gc.setStroke(Color.GRAY);
                gc.strokeRect(x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
            }
        }

        // Draw the robot as a red point
        gc.setFill(Color.RED);
        gc.fillOval(robotX * CELL_SIZE + CELL_SIZE / 4, robotY * CELL_SIZE + CELL_SIZE / 4, CELL_SIZE / 2, CELL_SIZE / 2);
    }

    @FXML
    private void handleStartGame() {
        // Create a Maze object and set the grid
        Maze mazeObj = new Maze(MAZE_WIDTH, MAZE_HEIGHT);
        mazeObj.setGrid(maze); // Set the generated maze grid
        mazeObj.setStartCell(maze[robotX][robotY]); // Set the robot's position as the start cell

        // Find the optimal path using A*
        AStar aStar = new AStar(mazeObj);
        List<Cell> optimalPath = aStar.findPath(); // Get the optimal path

        if (optimalPath.isEmpty()) {
            System.out.println("No path found to the goal!");
        } else {
            System.out.println("Path found with " + optimalPath.size() + " steps.");
            animateRobot(optimalPath); // Animate the robot along the optimal path
        }
    }

    private void animateRobot(List<Cell> path) {
        AnimationTimer timer = new AnimationTimer() {
            private int currentStep = 0;
            private long lastUpdate = 0;

            @Override
            public void handle(long now) {
                // Add a delay between steps (e.g., 500 milliseconds)
                if (now - lastUpdate >= 500_000_000) { // 500 milliseconds in nanoseconds
                    if (currentStep < path.size()) {
                        Cell currentCell = path.get(currentStep);
                        robotX = currentCell.getX();
                        robotY = currentCell.getY();
                        drawMaze(); // Redraw the maze with the robot in the new position
                        currentStep++;
                        lastUpdate = now;
                    } else {
                        stop(); // Stop the animation when the path is complete
                        System.out.println("Robot reached the goal!");
                    }
                }
            }
        };
        timer.start(); // Start the animation
    }

    @FXML
    private void handleTryAgain() {
        generateRandomMaze();
        drawMaze();
    }
}