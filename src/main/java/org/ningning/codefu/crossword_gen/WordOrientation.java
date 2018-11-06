package org.ningning.codefu.crossword_gen;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum WordOrientation {
    HORIZONTAL, VERTICAL;

    private static final List<WordOrientation> VALUES =
            Collections.unmodifiableList(Arrays.asList(values()));
    private static final int SIZE = VALUES.size();
    private static final Random RANDOM = new Random();

    public static WordOrientation randomOrientation()  {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }
}
