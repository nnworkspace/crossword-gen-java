package org.ningning.codefu.crossword_gen;

import com.google.common.base.Preconditions;
import java.util.Random;
import java.util.logging.Logger;

public class PlacementSpecGenerator {

    private final static Logger LOG = Logger.getLogger(PlacementSpecGenerator.class.getName());

    private Board board;

    public PlacementSpecGenerator(Board board) {
        this.board = board;
    }

    public PlacementSpec generateSpec(int shortestLength){

        Preconditions.checkArgument(shortestLength <= Math.min(board.getRows(), board.getCols()));

        // set orientation
        WordOrientation orientation = WordOrientation.randomOrientation();

        // set next random start position
        Random random = new Random();

        int[] startPosition = new int[2];
        if (orientation == WordOrientation.HORIZONTAL) {
            startPosition[0] = random.nextInt(board.getRows() - 1);
            startPosition[1] = random.nextInt(board.getCols() - shortestLength);
        } else {
            startPosition[0] = random.nextInt(board.getRows() - shortestLength);
            startPosition[1] = random.nextInt(board.getCols() - 1);
        }

        // set word length
        int maxLength = 0;
        if (orientation == WordOrientation.HORIZONTAL) {
            maxLength = board.getCols() - startPosition[1] + 1;
        } else {
            maxLength = board.getRows() - startPosition[0] + 1;
        }
        LOG.finer("Max word length is: " + maxLength);

        int wordLength = shortestLength;
        if (maxLength > wordLength) {
            wordLength = random.nextInt(maxLength - shortestLength) + shortestLength;
        }

        // return the placementSpec object
        PlacementSpec result = new PlacementSpec(this.board, orientation, startPosition, wordLength);
        LOG.info("Placement spec is: " + result.toString());
        return result;
    }
}
