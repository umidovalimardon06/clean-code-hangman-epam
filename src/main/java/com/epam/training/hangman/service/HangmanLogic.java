package com.epam.training.hangman.service;

import com.epam.training.hangman.interfaces.Hangman;
import com.epam.training.hangman.states.State;
import com.epam.training.hangman.utils.Common;
import com.epam.training.hangman.repository.InMemoryDatabase;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HangmanLogic implements Hangman {
    private final String guessedWord;
    private final List<Character> lettersTried;
    private int wrongGuessCount;
    private State state;

    public HangmanLogic(String guessedWord) {
        checkTheLiteralRequirements(guessedWord);

        this.guessedWord = guessedWord;
        this.lettersTried = new ArrayList<>();
        this.wrongGuessCount = 0;
        this.state = State.IN_PROGRESS;
    }

    @Override
    public void guess(char c) {
        char lowerCase = Character.toLowerCase(c);
        areWeAbleToContinueWithThisWord(lowerCase);
        lettersTried.add(lowerCase);

        if (isRightChar(lowerCase)) {
            boolean allRevealed = true;
            for (char wc : guessedWord.toCharArray()) {
                if (!lettersTried.contains(wc)) {
                    allRevealed = false;
                    break;
                }
            }
            state = allRevealed ? State.WON : State.IN_PROGRESS;
        } else {
            wrongGuessCount++;
            if (wrongGuessCount >= 7) state = State.LOST;
            else state = State.IN_PROGRESS;
        }
    }

    @Override
    public String getDisplayedWord() {
        if (state == State.LOST || state == State.WON) return guessedWord;
        return constructWordToDisplay();
    }

    @Override
    public State getState() {
        return this.state;
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

    private static void checkTheLiteralRequirements(String guessedWord) {
        if (Common.isNull(guessedWord)) throw new IllegalArgumentException("Guessed word can't be null");
        if (!isGuessedWordEnglishWord(
                guessedWord.toCharArray(),
                InMemoryDatabase.getAllowedChars())
        ) throw new IllegalArgumentException("Guessed word contains non English character");
    }


    private void areWeAbleToContinueWithThisWord(char lowerCase) {
        if (!isEnglishCharacter(lowerCase))
            throw new IllegalArgumentException("Given char is not exist in English character set");
        if (lettersTried.contains(lowerCase))
            throw new IllegalArgumentException("Character '" + lowerCase + "' has already been guessed");
    }

}