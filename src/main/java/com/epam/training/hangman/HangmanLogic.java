package com.epam.training.hangman;

import com.epam.training.hangman.interfaces.Hangman;
import com.epam.training.hangman.utils.Common;
import com.epam.training.hangman.utils.InMemoryDatabase;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HangmanLogic implements Hangman {
    public String guessedWord;
    public List<Character> lettersTried;
    public int wrongGuessCount;
    public String state;

    public HangmanLogic(String guessedWord) {
        if (Common.isNull(guessedWord)) throw new IllegalArgumentException("Guessed word can't be null");
        if (!isGuessedWordEnglishWord(
                guessedWord.toCharArray(),
                InMemoryDatabase.getAllowedChars())
        ) throw new IllegalArgumentException("Guessed word contains non English character");

        this.guessedWord = guessedWord;
        lettersTried = new ArrayList<>();
        wrongGuessCount = 0;
        state = "IN_PROGRESS";
    }

    @Override
    public void guess(char c) {
        char lowerCase = Character.toLowerCase(c);

        if (!isEnglishCharacter(lowerCase))
            throw new IllegalArgumentException("Given char is not exist in English character set");

        if (lettersTried.contains(lowerCase))
            throw new IllegalArgumentException("Character '" + lowerCase + "' has already been guessed");

        lettersTried.add(lowerCase);

        if (isRightChar(lowerCase)) {
            boolean allRevealed = true;
            for (char wc : guessedWord.toCharArray()) {
                if (!lettersTried.contains(wc)) {
                    allRevealed = false;
                    break;
                }
            }
            state = allRevealed ? "WON" : "IN_PROGRESS";
        } else {
            wrongGuessCount++;
            if (wrongGuessCount >= 7) {
                state = "LOST";
            } else {
                state = "IN_PROGRESS";
            }
        }
    }

    @Override
    public String getDisplayedWord() {
        if (state.equals("LOST") || state.equals("WON")) return guessedWord;
        return constructWordToDisplay();
    }

    @Override
    public State getState() {
        return State.valueOf(state);
    }

    @Override
    public List<Character> getLettersTried() {
        return new ArrayList<>(lettersTried);
    }

    @Override
    public int getWrongGuessesLeft() {
        return 7 - wrongGuessCount;
    }

    public static boolean isGuessedWordEnglishWord(char[] input, char[] charsAllowed) {
        Set<Character> allowedSet = new HashSet<>();
        for (char c : charsAllowed)
            allowedSet.add(c);

        for (char c : input)
            if (!allowedSet.contains(c)) return false;

        return true;
    }

    public static boolean isEnglishCharacter(char input) {
        char[] allowedChars = InMemoryDatabase.getAllowedChars();
        for (char c : allowedChars)
            if (c == input) return true;
        return false;
    }

    private boolean isRightChar(char c) {
        return guessedWord.contains(String.valueOf(c));
    }

    private String constructWordToDisplay() {
        StringBuilder wordToDisplay = new StringBuilder();
        for (int i = 0; i < guessedWord.length(); i++) {
            if (lettersTried.contains(guessedWord.charAt(i)))
                wordToDisplay.append(guessedWord.charAt(i));
            else
                wordToDisplay.append('_');
        }
        return wordToDisplay.toString();
    }
}