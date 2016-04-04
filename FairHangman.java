import java.util.*;
import java.io.*;

public class FairHangman implements HangmanGame{
    //global variables
    public int guesses;
    public List<Character> guessedLetters = new ArrayList<>();;
    public String secretWord;
    public int secretWordLength;
    public List<String> dict = new ArrayList<>();
    public List<Character> puzzleGuessed = new ArrayList<>();
    //constructors
    public FairHangman(){}
    public FairHangman (String filename) {convertFile(filename);}

    @Override
    public void initGame(int guessesAllowed) {commonReset(guessesAllowed);}

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

    @Override
    public boolean isGameOver() {
        if (isComplete() || (guesses <= 0)) {return true;}
        return false;
    }

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



    //abstraction
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
    //abstraction
    public void commonReset(int guessesAllowed){
        int randomIndex = (int)(Math.random()*(dict.size()));
        guesses = guessesAllowed;
        puzzleGuessed.clear();
        guessedLetters.clear();

        //pick a random secret word and find its length
        secretWord = dict.get(randomIndex);
        secretWordLength = secretWord.length();

        //set displayed puzzle (e.g."---y--") to the random word's length
        for(int i = 0; i < secretWordLength; i++){
            puzzleGuessed.add(BLANK);
        }
    }
    //abstraction
    public String getStringRepresentation(List<Character> list) {
        StringBuilder builder = new StringBuilder(list.size());
        for(Character ch: list) { builder.append(ch); }
        return builder.toString();
    }

    public boolean isDuplicate(char letter){
        if (!guessedLetters.contains(letter)) {
            guessedLetters.add(letter);
        } else {
            System.out.println("You guessed "+letter+" already, no penalty.");
            return false;
        }return true;
    }
}
