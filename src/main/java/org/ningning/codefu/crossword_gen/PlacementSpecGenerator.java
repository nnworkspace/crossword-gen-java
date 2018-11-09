package org.ningning.codefu.crossword_gen;

import com.google.common.base.Preconditions;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;
import org.apache.commons.math3.distribution.EnumeratedDistribution;
import org.apache.commons.math3.util.Pair;

public class PlacementSpecGenerator {

  private final static Logger LOG = Logger.getLogger(PlacementSpecGenerator.class.getName());

  private Board board;
  private int[] wordLengthCounts;
  private Random random;
  private List<Pair<Integer, Double>> wlCounts = new ArrayList<>();

  public PlacementSpecGenerator(Board board, int[] wordLengthCounts) {
    this.board = board;
    this.wordLengthCounts = wordLengthCounts;
    this.random = new Random();

    for (int i = 0; i < wordLengthCounts.length; i++) {
      Pair<Integer, Double> wCount = new Pair<>(i, new Double(this.wordLengthCounts[i]));
      this.wlCounts.add(i, wCount);
    }
  }

  public PlacementSpec generateSpec(int shortestLength) {

    Preconditions.checkArgument(shortestLength <= Math.min(board.getRows(), board.getCols()));

    // set orientation
    WordOrientation orientation = WordOrientation.randomOrientation();

    // set next random start position
    int[] startPosition = getStartPosition(orientation, shortestLength);

    // set word length
    int maxLength = 0;
    if (orientation == WordOrientation.HORIZONTAL) {
      maxLength = board.getCols() - startPosition[1] + 1;
    } else {
      maxLength = board.getRows() - startPosition[0] + 1;
    }
    LOG.finer("Max word length is: " + maxLength);

    EnumeratedDistribution distribution = new EnumeratedDistribution<Integer>(
        wlCounts.subList(shortestLength, maxLength));
    int wordLength = (int) distribution.sample();

//        int wordLength = shortestLength;
//        if (maxLength > wordLength) {
//            wordLength = random.nextInt(maxLength - shortestLength) + shortestLength;
//        }

    // return the placementSpec object
    PlacementSpec result = new PlacementSpec(this.board, orientation, startPosition, wordLength);
    LOG.info("Placement spec is: " + result.toString());
    return result;
  }

  private int[] getStartPosition(WordOrientation orientation, int shortestLength) {
    int[] startPosition = new int[2];
    if (orientation == WordOrientation.HORIZONTAL) {
      startPosition[0] = random.nextInt(board.getRows() - 1);
      startPosition[1] = random.nextInt(board.getCols() - shortestLength);
    } else {
      startPosition[0] = random.nextInt(board.getRows() - shortestLength);
      startPosition[1] = random.nextInt(board.getCols() - 1);
    }
    return startPosition;
  }
}
