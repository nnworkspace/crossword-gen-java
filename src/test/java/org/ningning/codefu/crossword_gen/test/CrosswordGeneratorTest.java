package org.ningning.codefu.crossword_gen.test;

import com.google.common.base.Stopwatch;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.ningning.codefu.crossword_gen.Board;
import org.ningning.codefu.crossword_gen.CrosswordGenerator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import java.util.stream.Stream;


public class CrosswordGeneratorTest {

    private final static Logger LOG = Logger.getLogger(CrosswordGeneratorTest.class.getName());

    private static String dictPath = "src/main/resources/german/german.dic";
    private static List<String> dict = new ArrayList<>();

    @BeforeAll
    public static void setUp() throws Exception {

        Stopwatch stopwatch = Stopwatch.createStarted();

        //read file into stream, try-with-resources
        try (Stream<String> stream = Files.lines(Paths.get(dictPath))) {
            stream.forEach(line -> dict.add(line));
        } catch (IOException e) {
            e.printStackTrace();
        }

        stopwatch.stop();
        LOG.info(
                String.format("Time used for loading dictionary: %d ms.",
                        stopwatch.elapsed(TimeUnit.MILLISECONDS)));
    }

    @Test
    public void testConstructor() {
        Board board = new Board(8, 8);

        CrosswordGenerator cwGenerator = new CrosswordGenerator(dict, board);

        cwGenerator.getDict().stream().limit(10).forEach( word ->
                System.out.println(word)
        );

        // LOG.info();
    }

    @Test
    public void testGenerate() {
        Board board = new Board(12, 12);


        CrosswordGenerator cwGenerator = new CrosswordGenerator(dict, board);

        cwGenerator.generate(0.875, 5);
        cwGenerator.getBoard().fillEmptyCellsWithRandomChars();

        // TODO

//        cwGenerator.getDict().stream().limit(10).forEach( word ->
//                System.out.println(word)
//        );

        Board result = cwGenerator.getBoard();
        List<String> words = cwGenerator.getPlacedWords();

        System.out.println("================================================");

        System.out.println(result);
        System.out.println(words);

        System.out.println(cwGenerator.getPuzzleAndSolution());
    }
}