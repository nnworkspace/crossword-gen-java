package org.ningning.codefu.crossword_gen;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Logger;

public class Runner {
    private final static Logger LOG = Logger.getLogger(Runner.class.getName());

    public static void main(String[] args) {
        //long totalWords = getTotalWords(args[0]);
        Board board = new Board(Integer.parseInt(args[1]), Integer.parseInt(args[2]));
        CrosswordGenerator cg = new CrosswordGenerator(Paths.get(args[0]), board);
        cg.generate(Double.parseDouble(args[3]), Integer.parseInt(args[4]));

        Board result = cg.getBoard();
        List<String> words = cg.getPlacedWords();

        System.out.println("================================================");

        System.out.println(result);
        System.out.println(words);
    }

    private static long getTotalWords(String dictPath) {
        LOG.finer("Entering method getTotalWords.....");

        Path path = Paths.get(dictPath);
        long lineCount = 0;
        try {
            lineCount = Files.lines(path).count();
        } catch (IOException e) {
            e.printStackTrace();
        }

        LOG.info(String.format("The dictionary has %d words.", lineCount));
        return lineCount;
    }
}
