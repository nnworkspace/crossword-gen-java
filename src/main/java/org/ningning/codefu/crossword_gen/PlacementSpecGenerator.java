package org.ningning.codefu.crossword_gen;

import java.util.Random;

public class PlacementSpecGenerator {

    private Board board;

    public PlacementSpecGenerator(Board board) {
        this.board = board;
    }

    public PlacementSpec generateSpec(){
        // get orientation
        WordOrientation orientation = WordOrientation.randomOrientation();

        // get next random start position
        Random random = new Random();

        int[] startPosition = new int[2];
        startPosition[0]  = random.nextInt(board.getRows());
        startPosition[1]  = random.nextInt(board.getCols());

        // get word length
        int maxLength = 0;
        if (orientation == WordOrientation.HORIZONTAL) {
            maxLength = board.getRows() - startPosition[0];
        } else {
            maxLength = board.getCols() - startPosition[1];
        }
        int wordLength = random.nextInt(maxLength);

        // return the placementSpec object
        return new PlacementSpec(this.board, orientation, startPosition, wordLength);
    }
}
