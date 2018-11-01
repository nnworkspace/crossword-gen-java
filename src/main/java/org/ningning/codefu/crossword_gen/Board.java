package org.ningning.codefu.crossword_gen;

public class Board {
    private int rows;
    private int cols;

    public Board(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
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

}
