# mazeGame

**Maze Solver with A\* Algorithm + JavaFX GUI**

## Description

mazeGame is a Java-based maze solver that uses the A\* algorithm to find the shortest path from a start point to a goal in a randomly generated maze.  
It comes with a graphical user interface (GUI) built using JavaFX, which visualizes the maze, the robot’s movement, and the resulting optimal path.

## Features

- Random maze generation  
- Pathfinding using the A\* algorithm  
- Visualization of the maze grid, start/end points, and the computed path  
- GUI interaction via JavaFX  
- Customizable maze size (optional — depending on your implementation)  

## Tech Stack

- Java (language)  
- JavaFX (for GUI)  
- Maven (for dependency management and build)  

## Installation & Running

1. Clone the repository:  
   ```bash
   git clone https://github.com/Mr-Haitham/mazeGame.git
   ```  
2. Navigate to the project directory:  
   ```bash
   cd mazeGame
   ```  
3. Build the project (using Maven wrapper):  
   ```bash
   ./mvnw clean compile 
   ```  
   or on Windows:  
   ```bash
   mvnw.cmd clean compile
   ```  
4. Run the application:  
   ```bash
   ./mvnw javafx:run
   ```  
   (adjust depending on your Maven/IDE setup)  

## Usage

Once running, the GUI will display a randomly generated maze. The start and end points are defined in the maze.  
The program will compute the optimal path using A\* and display the path in the GUI — showing how the “robot” moves from start to end.  

Feel free to modify maze size or solver parameters (if you add them) in the source code under `src/`.  

## Project Structure

```
mazeGame/
├── src/               ← source code  
│   ├── main/  
│   └── ...  
├── pom.xml            ← Maven build file  
├── mvnw, mvnw.cmd     ← Maven wrapper scripts  
└── .gitignore  
```  

## Contributing

Contributions are welcome! If you wish to contribute:  
- Fork the repo  
- Create a feature branch (`git checkout -b feature-name`)  
- Commit your changes  
- Submit a Pull Request describing your changes  

## License

This project is licensed under the [MIT License](LICENSE) – see the LICENSE file for details.

## Acknowledgments

Project idea and inspiration, plus pathfinding logic credit inspired by typical A\* algorithm implementations.  
If you used any external libraries or references, mention them here.  
