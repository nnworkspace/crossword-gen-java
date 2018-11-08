package org.ningning.codefu.crossword_gen;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CrosswordGenerator {

  private final static Logger LOG = Logger.getLogger(CrosswordGenerator.class.getName());

  private List<String> dict;
  private Board board;
  private List<PlacementContext> placementHistory = new ArrayList<>();

  private PlacementSpecGenerator pSpecGenerator;
  private Random random = new Random();

  public CrosswordGenerator(Path dictPath, Board board) {
    this.loadDict(dictPath);
    this.init(board);
  }

  public CrosswordGenerator(List<String> dict, Board board) {
    this.dict = dict;
    this.init(board);
  }

  private void init(Board board) {
    this.board = board;
    this.pSpecGenerator = new PlacementSpecGenerator(board);
  }

  public void generate() {
    List<String> shortDict = shortenWordList();

    int totalCells = this.board.countTotalCells();

    float density = 0.75f;

    while (this.board.countEmptyCells() > totalCells * (1.0 - density)) {

      // randomly pick the following specification:
      // * horizon or vertical
      // * start from which cell
      // * a suitable word length
      PlacementSpec pSpec = pSpecGenerator.generateSpec(4);

      // build the holder for the new word
      char[] newWord = board.getNewWordHolder(pSpec);
      List<String> candidates = findCandidates(pSpec, newWord);

      // if there's any candidate, randomly choose a word from the candidate list
      // place it into the board and put the whole placement onto a stack.
      if (!candidates.isEmpty()) {

        placeANewWord(pSpec, candidates);

      } else {

        continue;
        //resetLastPlacement();
      }

      // TODO
    }
  }

  public List<String> getDict() {
    return this.dict;
  }

  public Board getBoard() {
    return this.board;
  }

  public List<String> getPlacedWords() {
    return this.placementHistory.stream().map(pContext -> pContext.getWord())
        .collect(Collectors.toList());
  }

  private List<String> findCandidates(PlacementSpec pSpec, char[] newWord) {
    // make a pattern for string matching using the content in the new word holder
    StringBuilder regexBuilder = new StringBuilder();
    for (int i = 0; i < pSpec.getWordLength(); i++) {
      if (newWord[i] == ' ') {
        regexBuilder.append("\\w");
      } else {
        regexBuilder.append(newWord[i]);
      }
    }

    String regex = regexBuilder.toString();

    List<String> candidates = this.shortenWordList().stream().filter(word ->
        word.matches(regex)
    ).collect(Collectors.toList());

    LOG.info("Candidates size: " + candidates.size());

    if (!candidates.isEmpty()) {
      LOG.info("Candidates: " + candidates.get(0) + "....");
    }
    return candidates;
  }

  private void placeANewWord(PlacementSpec pSpec, List<String> candidates) {
    String word = candidates.get(random.nextInt(candidates.size()));
    board.putWord(word, pSpec);
    PlacementContext pContext = new PlacementContext(this.board, word, candidates, pSpec);
    this.placementHistory.add(pContext);
  }

  private void resetLastPlacement() {
    if (!this.placementHistory.isEmpty()) {
      PlacementContext previousContext = this.placementHistory.get(placementHistory.size() - 1);

      List<String> previousCandidates = previousContext.getCandidates();
      previousCandidates.remove(previousContext.getWord());

      if (previousCandidates.isEmpty()) {
        // in this case, previous placement will be removed from the history
        this.placementHistory.remove(previousContext);
        return;

      } else {

        Board prevPrevBoard = this.placementHistory.get(placementHistory.size() - 2).getBoard();
        // use the previous placement specification
        PlacementSpec lastSpec = previousContext.getPlacementSpec();
        String word = previousCandidates.get(random.nextInt(previousCandidates.size() - 1));
        previousCandidates.remove(previousContext.getWord());
        prevPrevBoard.putWord(word, lastSpec);
        PlacementContext newContext = new PlacementContext(prevPrevBoard, word, previousCandidates,
            lastSpec);
        this.placementHistory.remove(placementHistory.size() - 1);
        this.placementHistory.add(newContext);
        return;
      }

    } else {
      // an edge case that is not likely to happen
      // TODO
    }
  }

  private void loadDict(Path dictPath) {
    this.dict = new ArrayList<>();

    //read file into stream, try-with-resources
    try (Stream<String> stream = Files.lines(dictPath)) {
      stream.forEach(line -> dict.add(line));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private List<String> shortenWordList() {

    List<String> result = this.dict.stream()
        .filter(word -> word.length() <= this.board.getLongestSide() && !word.contains(" "))
        .map(String::toUpperCase)
        .collect(Collectors.toList());

    LOG.info(String.format("Dictionary has %d words, shortened word list has words: %d",
        dict.size(), result.size()));
    return result;
  }
}
