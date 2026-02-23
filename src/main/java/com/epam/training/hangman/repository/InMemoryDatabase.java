package com.epam.training.hangman.repository;

public class InMemoryDatabase {
    public static String[] getLetters() {
        return new String[]{"hangman", "apple", "bee", "clean", "computer", "office", "recursion"};
    }

    public static char[] getAllowedChars() {
        return new char[]{'a','b','c','d','e','f','g','h',
                'i','j','k','l','m','n','o','p','q','r','s'
                ,'t','u','v','w','x','y','z'
        };
    }
}
