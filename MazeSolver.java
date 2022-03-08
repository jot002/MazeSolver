///////////////////////////////////////////////////////////////////////////////
// Title:              MazeSolver
// Files:              Main.java, MainPoint.java
// Quarter:            CSE 8B Spring 2021
//
// Author:             Jonathan Tran
// Email:              jot002@ucsd.edu
// Instructor's Name:  Professor Allos
//
///////////////////////////////////////////////////////////////////////////////

import java.io.IOException;
import java.util.Scanner;
import java.io.File;

/**
 * Creates an 2d array of Mazepoints called maze that contains the maze
 * representation
 *
 * Bugs: None known
 *
 * @author   Jonathan Tran
 */
public class MazeSolver {

    private MazePoint[][] maze;     // Used to store maze representation

    /**
     * Public no-arg constructor
     */
    public MazeSolver() {
        this.maze = null;
    }

    /**
     * Public getter that retrieves private member variables - maze.
     *
     * @return return the member variable - maze.
     */
    public MazePoint[][] getMaze() {
        return this.maze;
    }

    /**
     * Public setter that overrides private member variables - maze.
     *
     * @param maze Maze to be stored.
     */
    public void setMaze(MazePoint[][] maze) {
        this.maze = maze;
    }

    /**     
    * This constructor reads the maze file fileToRead and parses the maze
    * into a 2d array of MazePoint objects
    *
    * @param fileToRead - file name of the file that will be read
    */
    public MazeSolver(String fileToRead) throws IOException {
        Scanner scanner = new Scanner(new File(fileToRead));
        // initializing rows and columns to the number of rows and columns 
        // of the 2d array in the fileToRead
        int rows = scanner.nextInt();
        int columns = scanner.nextInt();
        this.maze = new MazePoint[rows][columns];
        // Reads file and converts it to a 2D array
        while(scanner.hasNextLine()) {
            for (int r = 0; r < rows; r++) {
                for (int c = 0; c < columns; c++) {
                    //puts symbol at maze position r and c
                    this.maze[r][c] = new MazePoint(scanner.next());
                }
                // goes to the next line to get more symbols
                scanner.nextLine();
            }
        }
        // closes scanner
        scanner.close();
    }

    /**
     * Public escape from maze to be called by user. It invokes helper methods
     * that either use for-loop implementation or while-loop implementation.
     * 
     * Precondition: this.maze has been loaded and valid (rectangular).
     * 
     * Postcondition: this.maze has been escaped and path has been recorded.
     * 
     * @param mode Whether to use for-loop implementation or while-loop 
     * implementation.
     */
    public void escapeFromMaze(String mode) {
        if (mode.equals("for")) {
            this.escapeFromMazeForLoop();
        } else if (mode.equals("while")) {
            this.escapeFromMazeWhileLoop();
        }
    }
    
    /**     
    * Using a for loop, it checks if the area to the right is empty and if 
    * the current position is a path and if it is, it will create a path to
    * the right. If not, it will check if the area below is empty, and if 
    * it is, it will create a path there. This method creates an escape path
    * in the maze.
    * No parameters
    * No returns - creates a path in the maze
    */
    private void escapeFromMazeForLoop() {
        // set path and starting position at the top left corner
        MazePoint position = maze[0][0];
        position.setToPath();
        for (int i = 0; i < this.maze.length; i++) {
            for (int j = 0; j < this.maze[0].length-1; j++) {
                // sets path if the space to the right is empty
                if (maze[i][j+1].isEmpty() && maze[i][j].isPath()) {
                    position = maze[i][j+1];
                    position.setToPath();
                }
                // sets path if the space below is empty
                else if (i != this.maze.length-1) {
                    if (maze[i+1][j].isEmpty() && maze[i][j].isPath()) {
                        position = maze[i+1][j];
                        position.setToPath();
                        break;
                    }
                
                }
            }
        }
        // sets path at the bottom right corner
        position = maze[this.maze.length-1][this.maze[0].length-1];
        position.setToPath();
    }

    /**     
    * Using a while loop, it checks if the area to the right is empty and if 
    * the current position is a path and if it is, it will create a path to
    * the right and increment the columns index by 1. If not, it will check 
    * if the area below is empty, and if it is, it will create a path there
    * and increment the row index by 1. This method creates an escape path 
    * in the maze.
    * No parameters
    * No returns - creates a path in the maze
    */
    private void escapeFromMazeWhileLoop() {
        int i = 0;
        int j = 0;
        // sets path and starting position in the top left corner
        MazePoint position = maze[0][0];
        position.setToPath();
        while ((i < (this.maze.length)) && (j < (this.maze[0].length-1))) {
            // sets path if the space to the right is empty
            if (maze[i][j+1].isEmpty() && maze[i][j].isPath()) {
                // increments the column index if we move to the right
                j += 1;
                position = maze[i][j];
                position.setToPath();
               
            }
            // sets path if the space below is empty
            else if (i != this.maze.length-1) {
                if (maze[i+1][j].isEmpty() && maze[i][j].isPath()) {
                    // increments the row index if we move down
                    i += 1;
                    position = maze[i][j];
                    position.setToPath();
                }
            }
        }
        // sets path at the bottom right corner
        position = maze[this.maze.length-1][this.maze[0].length-1];
        position.setToPath();
    }

    /**     
    * Constructs a string representation of this.maze and returns it.
    * No parameters
    * @return stringOutput- string representation of this.maze
    */
    public String mazeToString() {
        // creates an empty string
        String stringOutput = "";
        for (int i = 0; i < this.maze.length; i++) {
            for (int j = 0; j < this.maze[0].length; j++) {
                if (j != this.maze[0].length-1) {
                    // adds the next symbol plus a space to stringOutput
                    stringOutput = stringOutput + maze[i][j].getSymbol() 
                                    + " ";
                }
                else {
                    // adds the last symbol and "\n" to stringOutput
                    stringOutput = stringOutput + maze[i][j].getSymbol()
                                    + "\n";
                }
            }
        }
        return stringOutput;
    }

    /**     
    * Takes in a 2d array of MazePoint objects and compares it to the argument
    * that contains the member variable this.maze. It checks if they match, 
    * returning true if they do and false if they do not.
    * @param other - 2d array of MazePoint objects
    * @return true/false - depending on if the two 2d arrays are 
    * indistinguishable
    */
    public boolean mazeMatch(MazePoint[][] other) {
        //comparing the dimensions of the 2d arrays
        if ((this.maze.length == other.length) && 
        (this.maze[0].length == other[0].length)) {
            for (int i=0; i<this.maze.length; i++) {
                for (int j=0; j<this.maze[0].length; j++) {
                    if 
                    // checks if the two mazes at the position are not equal
                    (!maze[i][j].getSymbol().equals(other[i][j].getSymbol())){
                        return false;
                    }
                    
                }
            }
            // return true if the two 2d arrays match
            return true;
        }
        // returns false if the dimensions are different
        return false; 
    }
}
