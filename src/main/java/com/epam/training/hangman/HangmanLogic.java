package com.epam.training.hangman;

import com.epam.training.hangman.interfaces.Hangman;
import com.epam.training.hangman.utils.Common;
import com.epam.training.hangman.utils.InMemoryDatabase;
import com.epam.training.hangman.utils.State;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HangmanLogic implements Hangman {
    public String guessedWord;
    public Set<Character> lettersTried;
    public int wrongGuessCount;
    public static State state;

    public HangmanLogic(String guessedWord) {
        if (Common.isNull(guessedWord)) throw new IllegalArgumentException("Guessed word can't be null");
        if (!isGuessedWordEnglishWord(
                guessedWord.toCharArray(),
                InMemoryDatabase.getAllowedChars())
        ) throw new IllegalArgumentException("Guessed word contains non English character");

        this.guessedWord = guessedWord;
        lettersTried = new HashSet<>();
        wrongGuessCount = 0;
    }

    @Override
    public void guess(char c) {
        char lowerCase = Character.toLowerCase(c);

        if (!isEnglishCharacter(lowerCase))
            throw new IllegalArgumentException("Given char is not exist in English character set");

        boolean isNewGuess = lettersTried.add(lowerCase);
        if (!isNewGuess) return;


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
            if (wrongGuessCount >= 7) {
                state = State.LOST;
            } else state = State.IN_PROGRESS;
        }
    }

    @Override
    public String getDisplayedWord() {
        if (state == State.LOST) return guessedWord;
        return constructWordToDisplay();
    }

    @Override
    public State getState() {
        if (state == State.WON) return State.WON;
        else if (state == State.LOST) return State.LOST;
        else return State.IN_PROGRESS;
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
        Set<Character> allowedSet = new HashSet<>();
        char[] allowedChars = InMemoryDatabase.getAllowedChars();
        for (char c : allowedChars)
            allowedSet.add(c);

        if (allowedSet.contains(input)) return true;

        return false;
    }


    private boolean isRightChar(char c) {
        return guessedWord.contains(String.valueOf(c));
    }

    private String constructWordToDisplay() {
        String wordToDisplay = "";
        for (int i = 0; i < guessedWord.length(); i++) {
            if (lettersTried.contains(guessedWord.charAt(i)))
                wordToDisplay += guessedWord.charAt(i);
            else wordToDisplay = wordToDisplay + '_';
        }
        return wordToDisplay;
    }


}