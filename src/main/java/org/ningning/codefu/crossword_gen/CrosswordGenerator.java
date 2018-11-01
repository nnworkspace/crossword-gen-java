package org.ningning.codefu.crossword_gen;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
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
        // TODO
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
}
