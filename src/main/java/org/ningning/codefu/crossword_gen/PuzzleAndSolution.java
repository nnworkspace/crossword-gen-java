package org.ningning.codefu.crossword_gen;

import java.util.Arrays;
import java.util.Map;

public class PuzzleAndSolution {

  private char[][] puzzle;
  private Map<String, PlacementSpec> solution;

  public PuzzleAndSolution(char[][] puzzle,
      Map<String, PlacementSpec> solution) {
    this.puzzle = puzzle;
    this.solution = solution;
  }

  public char[][] getPuzzle() {
    return puzzle;
  }

  public Map<String, PlacementSpec> getSolution() {
    return solution;
  }

  @Override
  public String toString() {
    return "PuzzleAndSolution{" +
        "puzzle=" + Arrays.toString(puzzle) +
        ", solution=" + solution +
        '}';
  }
}
