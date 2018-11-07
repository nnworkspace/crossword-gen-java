package org.ningning.codefu.crossword_gen;

public class Board {
    private int rows;
    private int cols;

    private char[][] charGrid = null;

    public Board(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.charGrid = new char[rows][cols];

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                this.charGrid[row][col] = ' ';
            }
        }
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public int getLongestSide() {
        return Math.max(this.rows, this.cols);
    }

    public String toString() {
        return String.format("Shape of this board is %d rows X %d cols", this.rows, this.cols).toString();
    }

    public char[][] getCharGrid() {
        return charGrid;
    }

    public char getChar(int row, int col) {
        return this.charGrid[row][col];
    }

    public void setChar(char c, int row, int col){
        this.charGrid[row][col] = c;
    }
}
