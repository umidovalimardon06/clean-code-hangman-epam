package com.epam.training.hangman.interfaces;

import com.epam.training.hangman.utils.State;
import java.util.List;

public interface Hangman {
    public void guess(char c);
    public String getDisplayedWord();
    public State getState();
    public List<Character> getLettersTried();
    public int getWrongGuessesLeft();
}
