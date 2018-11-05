package org.ningning.codefu.crossword_gen.test;

import org.junit.jupiter.api.Test;

import org.ningning.codefu.crossword_gen.Board;
import org.ningning.codefu.crossword_gen.Util;


public class UtilTest {

    @Test
    public void printAWord() {
        Board board = new Board(8, 8);
        Util.printAWord("Aachen", 'h', 2,3, board);

        System.out.println();
        Util.printAWord("Aachen", 'v', 2,3, board);
    }
}