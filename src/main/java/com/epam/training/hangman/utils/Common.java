package com.epam.training.hangman.utils;

import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.Scanner;

public class Common {
    public static final Scanner SC = new Scanner(System.in, StandardCharsets.UTF_8);
    public static final Random RANDOM = new Random();

    public static void line(){
        System.out.println();
    }

    public static <T> boolean isNull(T value ) {
        return value==null;
    }

}
