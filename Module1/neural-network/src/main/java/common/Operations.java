/*
 * Copyright (c) 21/8/2021, Pexers (https://github.com/Pexers)
 */
 
package common;

public class Operations {
    public static double logisticActivation(double input) { return 1 / (1 + Math.exp(-input)); }

    public static double tahnActivation(double input) { return Math.tanh(input); }

}
