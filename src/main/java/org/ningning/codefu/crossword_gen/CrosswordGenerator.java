package org.ningning.codefu.crossword_gen;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CrosswordGenerator {
    private final static Logger LOG = Logger.getLogger(CrosswordGenerator.class.getName());

    private List<String> dict;
    private Board board;

    private PlacementSpecGenerator pSpecGenerator;

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
        this.pSpecGenerator = new PlacementSpecGenerator(board);
    }

    public void generate() {
        List<String> shortDict = shortenWordList();

        // randomly pick the following specification:
        // * horizon or vertical
        // * start from which cell
        // * a suitable word length
        PlacementSpec pSpec = pSpecGenerator.generateSpec();
        LOG.info("Placement spec is: " + pSpec.toString());

        // build the holder for the new word
        WordOrientation orientation = pSpec.getOrientation();
        int length = pSpec.getWordLength();

        char[] newWord = new char[length];

        int[] startPos = pSpec.getStartPosition();
        int startRow = startPos[0];
        int startCol = startPos[1];

        if (WordOrientation.HORIZONTAL == orientation) {
            for (int i = 0, col = startCol ; i < newWord.length; i++, col++) {
                newWord[i] = this.board.getChar(startRow, col);
            }
        } else {
            for (int i = 0, row = startRow; i < newWord.length; i++, row++) {
                newWord[i] = this.board.getChar(row, startCol);
            }
        }

        LOG.info("New word holder looks like this: " + Arrays.toString(newWord));

        // make a pattern for string matching using the content in the new word holder
        StringBuilder regexBuilder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            if (newWord[i] == ' ') {
                regexBuilder.append("\\w");
            } else {
                regexBuilder.append(newWord[i]);
            }
        }

        String regex = regexBuilder.toString();

        List<String> candidates = this.shortenWordList().stream().filter( word ->
                word.matches(regex)
        ).collect(Collectors.toList());

        LOG.info("Candidates size: " + candidates.size());

        if (candidates.size() > 0) {
            LOG.info("Candidates: " + candidates.get(0) + "....");
        }

        // if there's any candidate, randomly choose a word from the candidate list
        // place it into the board and put the whole placement onto a stack.

        // TODO

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
                .filter(word -> word.length() <= this.board.getLongestSide() && !word.contains(" "))
                .map(String::toUpperCase)
                .collect(Collectors.toList());

        LOG.info(String.format("Dictionary has %d words, shortened word list has words: %d",
                dict.size(), result.size()));
        return result;
    }
}
