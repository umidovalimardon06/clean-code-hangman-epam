package com.epam.training.hangman;

import com.epam.training.hangman.interfaces.Hangman;
import com.epam.training.hangman.utils.Common;
import com.epam.training.hangman.utils.InMemoryDatabase;

import java.util.List;

public class Application {
    public String word;
    public List<Character> lettersTried;
    public int wrongGuessCount;

    public static void main(String[] args) {
        String[] reservedLetters = InMemoryDatabase.getLetters();
        int randomIndexBasedOnLettersArrayLength = Common.RANDOM.nextInt(reservedLetters.length);
        String guessedWord = reservedLetters[randomIndexBasedOnLettersArrayLength];

        Hangman hangmanGame = new HangmanLogic(guessedWord);
        System.out.println("Welcome to the Hangman game!");
        Common.line();

        String s;
        do {
            System.out.println("The word: " + hangmanGame.getDisplayedWord());
            System.out.println("Letters tried: " + hangmanGame.getLettersTried());
            System.out.println("Wrong guesses until game over: " + hangmanGame.getWrongGuessesLeft());

            while (true) {
                String in = "";
                boolean stay = true;
                while (stay) {
                    System.out.print("Enter your guess: ");
                    in = Common.SC.next();

                    System.out.println();
                    if (in.length() == 1) stay = false;
                    else {
                        System.out.println("Error: please enter a single character only!");
                        System.out.println();
                    }
                }
                try {
                    // guessing
                    hangmanGame.guess(in.charAt(0));
                    break;
                } catch(Exception e) {
                    System.out.println(e.getMessage());
                    System.out.println();
                }
            }
        } while (hangmanGame.getState() == State.IN_PROGRESS);

        // end
        if (hangmanGame.getState() == State.WON) {
            System.out.println("Congratulations, you won!");
            System.out.println("The word was: " + guessedWord);
        } else {
            System.out.println("You have no more tries left, you lost the game");
            System.out.println("The word was: " + guessedWord);
        }
    }

}