package com.epam.training.hangman;

import com.epam.training.hangman.interfaces.Hangman;
import com.epam.training.hangman.service.HangmanLogic;
import com.epam.training.hangman.states.State;
import com.epam.training.hangman.utils.Common;
import com.epam.training.hangman.repository.InMemoryDatabase;


public class Application {
    public static void main(String[] args) {
        String guessedWord = getGuessedWord();
        Hangman hangmanGame = new HangmanLogic(guessedWord);

        System.out.println("Welcome to the Hangman game!");
        Common.line();

        do {
            showHangmanDialog(hangmanGame);
            String userGuessedCharacter;

            do {
                userGuessedCharacter = takeTheGuess();
                if (isSingleChar(userGuessedCharacter)) singleCharacterError();
            } while (isSingleChar(userGuessedCharacter));

            hangmanGame.guess(userGuessedCharacter.charAt(0));
        } while (hangmanGame.getState() == State.IN_PROGRESS);
        winOrLose(hangmanGame, guessedWord);
    }

    private static boolean isSingleChar(String userGuessedCharacter) {
        return userGuessedCharacter.length() != 1;
    }

    private static void showHangmanDialog(Hangman hangmanGame) {
        System.out.println("The word: " + hangmanGame.getDisplayedWord());
        System.out.println("Letters tried: " + hangmanGame.getLettersTried());
        System.out.println("Wrong guesses until game over: " + hangmanGame.getWrongGuessesLeft());
    }

    private static String takeTheGuess() {
        String in;
        System.out.print("Enter your guess: ");
        in = Common.SC.next();
        Common.line();
        return in;
    }

    private static void singleCharacterError() {
        System.out.println("Error: please enter a single character only!");
        Common.line();
    }

    private static void winOrLose(Hangman hangmanGame, String guessedWord) {
        if (hangmanGame.getState() == State.WON) {
            System.out.println("Congratulations, you won!");
            System.out.println("The word was: " + guessedWord);
        } else {
            System.out.println("You have no more tries left, you lost the game");
            System.out.println("The word was: " + guessedWord);
        }
    }

    private static String getGuessedWord() {
        String[] reservedLetters = InMemoryDatabase.getLetters();
        int randomIndexBasedOnLettersArrayLength = Common.RANDOM.nextInt(reservedLetters.length);
        return reservedLetters[randomIndexBasedOnLettersArrayLength];
    }

}