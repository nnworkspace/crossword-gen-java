package org.ningning.codefu.crossword_gen.test;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.ningning.codefu.crossword_gen.Board;
import org.ningning.codefu.crossword_gen.CrosswordGenerator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Stream;


public class CrosswordGeneratorTest {

    private final static Logger LOG = Logger.getLogger(CrosswordGeneratorTest.class.getName());

    private static String dictPath = "src/main/resources/german/german.dic";
    private static List<String> dict = new ArrayList<>();

    @BeforeAll
    public static void setUp() throws Exception {

        //read file into stream, try-with-resources
        try (Stream<String> stream = Files.lines(Paths.get(dictPath))) {
            stream.forEach(line -> dict.add(line));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    @Test
//    public void testConstructor() {
//        Board board = new Board(8, 8);
//
//        CrosswordGenerator cwGenerator = new CrosswordGenerator(dict, board);
//
//        cwGenerator.getDict().stream().limit(10).forEach( word ->
//                System.out.println(word)
//        );
//
//        // LOG.info();
//    }

    @Test
    public void testGenerate() {
        Board board = new Board(8, 8);

        CrosswordGenerator cwGenerator = new CrosswordGenerator(dict, board);

        cwGenerator.generate();

        // TODO

        cwGenerator.getDict().stream().limit(10).forEach( word ->
                System.out.println(word)
        );
    }

}