import java.util.*;
import java.io.*;

public class FairHangman extends BaseHangman{

    //constructor
    public FairHangman (String filename) {convertFile(filename);}

    @Override
    public void initGame(int guessesAllowed) {commonReset(guessesAllowed);}

    @Override
    public boolean updateWithGuess(char letter) {

        //check if not duplicate, don't add if duplicate
        if (!isDuplicate(letter)) {return false;}

        boolean guess = false;
        //look for given letter in the secret word
        for(int i = 0; i < secretWordLength; i++) {
            if (letter == secretWord.charAt(i)){
                puzzleGuessed.remove(i);
                puzzleGuessed.add(i, secretWord.charAt(i));
                guess = true;
            }
        }
        //if none of given letter was found, subtract a guess
        if (!guess) { guesses--; }
        return guess;
    }
}
