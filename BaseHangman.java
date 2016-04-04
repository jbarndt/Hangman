import java.io.*;
import java.util.*;

/**
 * CS 251 Lab 6 - BaseHangman
 * @author Jonathan Barndt
 * @version date 2016-04-04
 * An abstract class that takes all the common global variables and methods
 * of the EvilHangman and FairHangman classes, and also implements HangmanGame.
 */
public abstract class BaseHangman implements HangmanGame{

    //global variables
    public int guesses;
    public List<Character> guessedLetters = new ArrayList<>();;
    public String secretWord;
    public int secretWordLength;
    public List<String> dict = new ArrayList<>();
    public List<Character> puzzleGuessed = new ArrayList<>();

    //shared implementation methods
    @Override
    public boolean isGameOver() {
        if (isComplete() || (guesses <= 0)) {return true;}
        return false;
    }

    @Override
    public int getGuessesRemaining() {return guesses;}

    @Override
    public Collection<Character> getGuessedLetters() {return guessedLetters;}

    @Override
    public String getPuzzle() {return getStringRepresentation(puzzleGuessed);}

    @Override
    public String getSecretWord() {return secretWord;}

    @Override
    public boolean isComplete() {
        for (int i = 0; i < secretWordLength; i++){
            if (puzzleGuessed.get(i).equals(BLANK)){
                return false;
            }
        } return true;
    }

    //shared methods

    /**
     * Returns the string representation of given list of characters.
     *
     * @param list The given file.
     * @return String converted from char list
     */
    public String getStringRepresentation(List<Character> list) {
        StringBuilder builder = new StringBuilder(list.size());
        for(Character ch: list) { builder.append(ch); }
        return builder.toString();
    }

    /**
     * Converts a .txt file to a list of strings.
     *
     * @param filename The given file.
     */
    public void convertFile(String filename){
        File file = new File(filename);
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                dict.add(line);
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Resets all common bookkeeping for a new game.
     *
     * @param guessesAllowed The given file.
     */
    public void commonReset(int guessesAllowed){
        guesses = guessesAllowed;
        puzzleGuessed.clear();
        guessedLetters.clear();

        //pick a random secret word and find its length
        int randomIndex = (int)(Math.random()*(dict.size()));
        secretWord = dict.get(randomIndex);
        secretWordLength = secretWord.length();

        //set displayed puzzle (e.g."---y--") to the random word's length
        for(int i = 0; i < secretWordLength; i++){
            puzzleGuessed.add(BLANK);
        }
    }

    /**
     * Checks if given letter has already been guessed.
     *
     * @param letter the char to check
     * @return False if duplicate letter (else, return true)
     */
    public boolean isDuplicate(char letter){
        if (!guessedLetters.contains(letter)) {
            guessedLetters.add(letter);
        } else {
            System.out.println("You guessed "+letter+" already, no penalty.");
            return false;
        }return true;
    }
}
