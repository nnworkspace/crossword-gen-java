# A Generator for Word Search Puzzle in Java

This program can generate word search puzzles. You can specify the board size, 
density of the filling of the board, and the shortest length of words to be used.

Not completely implemented yet.....

Usage:
```
gradlew runApp
```

Output of a test with a board 20 * 20, density 0.75, shortest length of 5, looks like this:

```

================================================
Shape of this board is 20 rows X 20 cols

 W B C J E T M A U S P O R T Y O D A R I 
 M C T G H I R N W U T F A W L C W Y K K 
 C T K E M E I G E N I N N E N W A L L I 
 A R E S Z H A O H O K T A L L E N N O X 
 C A W E S S I S R T B A L A N C E N N A 
 H U B H A U S T M L L U T S C H N E E Z 
 E B B E N F H U A R A K A R O S K B N L 
 N E N N U M E R N O I Y S R G E R E D E 
 V E E E B I L A N Z R I C H T L I N I E 
 P I U M G T S A S B A C H S I G E G I V 
 A N W A L T L I C H E S E G O E G E C M 
 I D I N Y E A Y H V S F N E O S L S R C 
 T R E W M L P P A X F F K A R E N T E E 
 H U D E C H I F F R E O E A I T S E M X 
 E C E S N U D F T P L L L N Z Z U I T F 
 O K H E A F A E S O D D W G O P S N E J 
 P T O N M E R D A F E E E E X O H A T V 
 H E P S A N E E R E R R S L A W I S T R 
 O N F X R A M R Z N W A P P N E T G I C 
 R M S T A P C B T P A F E P H S Z F W K 

[ANALTASCHEN, ANGEL, ANGOSTURA, ANWALTLICHES, ANWESENS, ASBACHS, BALANCEN, 
BEEINDRUCKTEN, BILANZRICHTLINIE, CACHEN, CHIFFRE, CREMTE, EBBEN, EIGEN, 
FEDER, FOLDER, GEREDE, GESEHENEM, HIRNWUT, HUBHAUS, INNENWALL, KAREN, KAROS, 
KLONEND, LAPIDAREM, LENNOX, LUTSCH, MANNSCHAFTSARZT, MAUSPORT, MCNAMARA, 
MITTELHUFEN, NEBENGESTEIN, NEUWIED, OKTAL, POFEN, RAESFELDER, SCHENKELWESPE, 
SLAWIST, SUSHI, THEOPHOR, TRAUBE, WANENKRIEG, WAPPNET, WECHSELGESETZ, 
WEHRMANN, WESSIS, WIEDEHOPFS]
```

The same program can also be used to generate crossword puzzle.

## Idea of the Algorithm

To be added....

## Dictionary Source

The German dictionary is downloaded from:

[https://sourceforge.net/projects/germandict/](https://sourceforge.net/projects/germandict/)

You can use other dictionary though. Each line in your dictionary file must contain a single word