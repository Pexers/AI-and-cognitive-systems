/*
 * Copyright (c) 21/8/2021, Pexers (https://github.com/Pexers)
 */
 
package planning;

public enum PathSymbols {
    GOAL('G'), WALL('W'), EMPTY('-'), PATH('X'), START('S');

    private final char symbol;

    PathSymbols(char symbol) {
        this.symbol = symbol;
    }

    public char getSymbol() {
        return this.symbol;
    }
}
