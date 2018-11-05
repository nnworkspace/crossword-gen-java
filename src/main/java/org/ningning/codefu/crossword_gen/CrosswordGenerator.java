package org.ningning.codefu.crossword_gen;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CrosswordGenerator {
    private final static Logger LOG = Logger.getLogger(CrosswordGenerator.class.getName());

    private List<String> dict;
    private Board board;

    public CrosswordGenerator(Path dictPath, Board board) {
        this.loadDict(dictPath);
        this.board = board;
    }

    public CrosswordGenerator(List<String> dict, Board board) {
        this.dict = dict;
        this.board = board;
    }

    public void generate() {
        List<String> shortDict = shortenWordList();

        // TODO
        // randomly pick the following specification:
        // * horizon or vertical
        // * start from which cell
        // * a suitable word length
        // check any crossing
        // then see if it's possible to fit into the board
    }

    public List<String> getDict() {
        return this.dict;
    }

    public Board getBoard() {
        return this.board;
    }

    private void loadDict(Path dictPath){
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

        LOG.info(String.format("Dictionary has %d words, shortened word list has words: %d",
                dict.size(), result.size()));
        return result;
    }
}
