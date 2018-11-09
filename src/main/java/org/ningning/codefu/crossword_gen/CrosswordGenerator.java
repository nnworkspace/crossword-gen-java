package org.ningning.codefu.crossword_gen;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CrosswordGenerator {

  private final static Logger LOG = Logger.getLogger(CrosswordGenerator.class.getName());

  private List<String> dict;
  private List<String> usedWords = new ArrayList<>();
  private Board board;
  private List<PlacementContext> placementHistory = new ArrayList<>();
  private PuzzleAndSolution puzzleAndSolution;

  private int[] wordLengthCounts = new int[100];

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

    // 1. filter out words with space
    // 2. count occurrences of each word's length
    this.dict = this.dict.stream()
        .filter(word -> !word.contains(" "))
        .map(String::toUpperCase)
        .collect(Collectors.toList());

    this.dict.stream().forEach(word -> this.wordLengthCounts[word.length()]++);

    StringBuilder sb = new StringBuilder("[");
    for (int i = 0; i < wordLengthCounts.length; i++) {
      sb.append("[").append(i).append(", ").append(this.wordLengthCounts[i]).append("], ");
    }
    sb.append("]");
    LOG.info("Lengths of words occurrences: " + sb.toString());

    this.pSpecGenerator = new PlacementSpecGenerator(this.board, this.wordLengthCounts);
  }

  public void generate(double density, int shortestLength) {
    List<String> shortDict = shortenWordList();

    int totalCells = this.board.countTotalCells();

    while (this.board.countEmptyCells() > totalCells * (1.0 - density)) {

      // randomly pick the following specification:
      // * horizon or vertical
      // * start from which cell
      // * a suitable word length
      PlacementSpec pSpec = pSpecGenerator.generateSpec(shortestLength);

      // build the holder for the new word
      char[] newWord = board.getNewWordHolder(pSpec);

      if (!validateNewWordHolder(newWord, shortDict)) {
        continue;
      }

      List<String> candidates = findCandidates(pSpec, newWord, shortDict);

      // if there's any candidate, randomly choose a word from the candidate list
      // place it into the board and put the whole placement onto a stack.
      if (!candidates.isEmpty()) {

        placeANewWord(pSpec, candidates, shortDict);

      } else {
        // TODO
        continue;
        //resetLastPlacement();
      }

      // build the puzzleAndSolution object
      Map<String, PlacementSpec> solutionMap = new TreeMap<>();
      this.placementHistory.forEach( placementContext -> {
        solutionMap.put(placementContext.getWord(), placementContext.getPlacementSpec());
      });

      this.puzzleAndSolution = new PuzzleAndSolution(this.board.getCharGrid(), solutionMap);
    }
  }

  public List<String> getDict() {
    return this.dict;
  }

  public Board getBoard() {
    return this.board;
  }

  public int[] getWordLengthCounts() {
    return wordLengthCounts;
  }

  public List<String> getPlacedWords() {
    return this.placementHistory.stream().map(pContext -> pContext.getWord()).sorted()
        .collect(Collectors.toList());
  }

  public PuzzleAndSolution getPuzzleAndSolution() {
    return puzzleAndSolution;
  }

  private List<String> findCandidates(PlacementSpec pSpec, char[] newWord, List<String> wordsPool) {
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

    List<String> candidates = wordsPool.stream().filter(word ->
        word.matches(regex)
    ).collect(Collectors.toList());

    LOG.info("Candidates size: " + candidates.size());

    if (!candidates.isEmpty()) {
      LOG.info("Candidates: " + candidates.get(0) + "....");
    }
    return candidates;
  }

  private void placeANewWord(PlacementSpec pSpec, List<String> candidates, List<String> wordsPool) {
    String word = candidates.get(random.nextInt(candidates.size()));

    String patternStr = "(\\w)*" + word + "(\\w)*";

    if (!usedWords.isEmpty()) {
      for (String used : usedWords) {
        if (used.matches(patternStr)) {
          wordsPool.remove(word);
          this.usedWords.add(word);
          return;
        }
      }
    }

    for (String used : usedWords) {
      String pattern = "(\\w)*" + used + "(\\w)*";
      if (word.matches(pattern)) {
        wordsPool.remove(word);
        this.usedWords.add(word);
        return;
      }
    }

    board.putWord(word, pSpec);
    PlacementContext pContext = new PlacementContext(this.board, word, candidates, pSpec);
    this.placementHistory.add(pContext);

    this.usedWords.add(word);
    wordsPool.remove(word);
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
        .filter(word -> word.length() <= this.board.getLongestSide())
        .collect(Collectors.toList());

//    LOG.info(String.format("Dictionary has %d words, shortened word list has words: %d",
//        dict.size(), result.size()));
    return result;
  }

  private boolean validateNewWordHolder(char[] newWordHolder, List<String> wordsPool) {
    int emptyCharCount = 0;
    for (char c : newWordHolder) {
      if (c == ' ') {
        emptyCharCount++;
      }
    }
    if (emptyCharCount <= 2 || emptyCharCount < newWordHolder.length * 0.5) {
      return false;
    }

    return true;
  }
}
