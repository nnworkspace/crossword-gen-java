package org.ningning.codefu.crossword_gen;

import java.util.Arrays;
import java.util.Random;
import java.util.logging.Logger;

public class Board {

  private final static Logger LOG = Logger.getLogger(Board.class.getName());

  private int rows;
  private int cols;

  private char[][] charGrid;

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

  public char[][] getCharGrid() {
    return charGrid;
  }

  public char getChar(int row, int col) {
    return this.charGrid[row][col];
  }

  public void setChar(char c, int row, int col) {
    this.charGrid[row][col] = c;
  }

  public int countTotalCells() {
    return rows * cols;
  }

  public int countEmptyCells() {
    int result = 0;

    for (int row = 0; row < rows; row++) {
      for (int col = 0; col < cols; col++) {
        if (this.charGrid[row][col] == ' ') {
          result++;
        }
      }
    }

    return result;
  }

  public char[] getNewWordHolder(PlacementSpec pSpec) {
    WordOrientation orientation = pSpec.getOrientation();

    char[] newWord = new char[pSpec.getWordLength()];

    int[] startPos = pSpec.getStartPosition();
    int startRow = startPos[0];
    int startCol = startPos[1];

    if (WordOrientation.HORIZONTAL == orientation) {
      for (int i = 0, col = startCol; i < newWord.length; i++, col++) {
        newWord[i] = getChar(startRow, col);
      }
    } else {
      for (int i = 0, row = startRow; i < newWord.length; i++, row++) {
        newWord[i] = getChar(row, startCol);
      }
    }

    LOG.info("New word holder looks like this: " + Arrays.toString(newWord));
    return newWord;
  }

  public void putWord(String word, PlacementSpec pSpec) {
    int[] startPos = pSpec.getStartPosition();
    int startRow = startPos[0];
    int startCol = startPos[1];

    if (WordOrientation.HORIZONTAL == pSpec.getOrientation()) {
      for (int i = 0, col = startCol; i < pSpec.getWordLength(); i++, col++) {
        setChar(word.charAt(i), startRow, col);
      }
    } else {
      for (int i = 0, row = startRow; i < pSpec.getWordLength(); i++, row++) {
        setChar(word.charAt(i), row, startCol);
      }
    }

    LOG.info(String.format("################## word: %s, the board looks like this:\n, %s", word,
        this.toString()));
  }

  public void fillEmptyCellsWithRandomChars() {
    final String alphabet = "ABCDEFGHIJKLMOPQRSTUVWXYZ";
    final int N = alphabet.length();

    Random r = new Random();

    for (int row = 0; row < rows; row++) {
      for (int col = 0; col < cols; col++) {
        if (this.charGrid[row][col] == ' ') {
          this.charGrid[row][col] = alphabet.charAt(r.nextInt(N));
        }
      }
    }
  }

  public String toString() {
    StringBuilder sb = new StringBuilder(
        String.format("Shape of this board is %d rows X %d cols", this.rows, this.cols).toString());
    sb.append("\n");

    // init the board view for console output
    char[][] boardView = initBoardView(rows, cols);

    for (int row = 0; row < this.charGrid.length; row++) {
      for (int col = 0; col < this.charGrid[row].length; col++) {
        boardView[row * 2 + 1][col * 2 + 1] = this.charGrid[row][col];
      }
    }

    for (int row = 0; row < boardView.length; row++) {
      for (int col = 0; col < boardView[row].length; col++) {
        char content = boardView[row][col];
        if (content != '-') {
          if (content == '|') {
            sb.append(' ');
          } else {
            sb.append(boardView[row][col]);
          }
        }
      }

      if (row % 2 == 0) {
        sb.append("\n");
      }
    }

    return sb.toString();
  }

  private char[][] initBoardView(int rows, int cols) {
    int viewRows = rows * 2 + 1;
    int viewCols = cols * 2 + 1;

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
          } else {
            boardView[row][col] = ' ';
          }
        }
      }
    }

    return boardView;
  }

}
