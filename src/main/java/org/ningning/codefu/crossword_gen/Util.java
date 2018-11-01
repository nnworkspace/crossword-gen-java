package org.ningning.codefu.crossword_gen;

import static com.google.common.base.Preconditions.checkArgument;

public class Util {

    public static void printAWord(String word, char mode,
                                  int beginRow, int beginCol, Board board) {
        checkArgument(word.length() <= board.getLongestSide(),
                "The word's length %d exceeds longest side of the board %d",
                word.length(), board.getLongestSide());

        // initialize the empty board
        char[][] boardView = initBoardView(board);

        switch (mode) {
            case 'h':
                setAWordInARow(word, beginRow, beginCol, boardView);
                break;
            case 'v':
                setAWordInACol(word, beginRow, beginCol, boardView);
                break;
            default:
                break;
        }

        // print the board
        printBoard(boardView);
    }

    private static char[][] initBoardView(Board board) {
        int viewRows = board.getRows() * 2 + 1;
        int viewCols = board.getCols() * 2 + 1;

        char[][] boardView = new char[viewRows][viewCols];

        for (int row = 0; row < viewRows; row++) {
            for (int col = 0; col < viewCols; col++) {
                if (row % 2 == 0) {
                    // even rows, are grid lines
                    boardView[row][col] = '-';
                } else {
                    if (col % 2 == 0) {
                        // even cols, are grid lines
                        boardView[row][col] = '|';
                    }  else {
                        boardView[row][col] = ' ';
                    }
                }
            }
        }

        return boardView;
    }

    private static void setAWordInARow(
            String word, int row, int beginCol, char[][] boardView) {
        int rowInView = row * 2 - 1;

        int beginColInView = beginCol * 2 - 1;

        for (int i = 0; i < word.length(); i++) {
            int colInView = beginColInView + i * 2;
            boardView[rowInView][colInView] = word.charAt(i);
        }
    }

    private static void setAWordInACol(
            String word, int beginRow, int col, char[][] boardView) {
        int colInView = col * 2 - 1;

        int beginRowInView = beginRow * 2 - 1;

        for (int i = 0; i < word.length(); i++) {
            int rowInView = beginRowInView + i * 2;
            boardView[rowInView][colInView] = word.charAt(i);
        }
    }

    private static void printBoard(char[][] boardView) {

        for (int row = 0; row < boardView.length; row++) {
            for (int col = 0; col < boardView[row].length; col++) {
                System.out.print(boardView[row][col]);
            }
            System.out.println();
        }
    }
}
