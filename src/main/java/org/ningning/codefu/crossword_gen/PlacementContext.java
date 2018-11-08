package org.ningning.codefu.crossword_gen;

import java.util.List;

public class PlacementContext {

  private Board board;
  private String word;
  private List<String> candidates;
  private PlacementSpec placementSpec;
  private int indexInStack = 0;

  public PlacementContext(Board board, String word, List<String> candidates,
      PlacementSpec placementSpec) {
    this.board = board;
    this.word = word;
    this.candidates = candidates;
    this.placementSpec = placementSpec;
    this.indexInStack ++;
  }

  public Board getBoard() {
    return board;
  }

  public String getWord() {
    return word;
  }

  public List<String> getCandidates() {
    return candidates;
  }

  public PlacementSpec getPlacementSpec() {
    return placementSpec;
  }

  public int getIndexInStack() {
    return indexInStack;
  }
}
