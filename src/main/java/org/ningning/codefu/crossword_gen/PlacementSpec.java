package org.ningning.codefu.crossword_gen;


import java.util.Arrays;

public class PlacementSpec {
    private WordOrientation orientation;

    // first int is row index, second int is col index
    private int[] startPosition; // = new int[2];

    private int wordLength;


    private Board board;

    public PlacementSpec(Board board, WordOrientation orientation, int[] startPosition, int wordLength) {
        this.board = board;
        this.orientation = orientation;
        this.startPosition = startPosition;
        this.wordLength = wordLength;
    }

    public WordOrientation getOrientation() {
        return orientation;
    }

    /**
     * @return first int is row index, second int is col index
     */
    public int[] getStartPosition() {
        return startPosition;
    }

    public int getWordLength() {
        return wordLength;
    }

    public Board getBoard() {
        return board;
    }


    @Override
    public String toString() {
        return "PlacementSpec{" +
                "orientation=" + orientation +
                ", startPosition=" + Arrays.toString(startPosition) +
                ", wordLength=" + wordLength +
                '}';
    }
}
