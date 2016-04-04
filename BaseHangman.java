import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Created by Jon on 3/23/16.
 */
public abstract class BaseHangman implements HangmanGame{

    int guesses;
    List<Character> guessedLetters = new ArrayList<>();;
    String secretWord;
    int secretWordLength;
    List<String> dict = new ArrayList<>();
    List<Character> puzzleGuessed = new ArrayList<>();

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
        guesses = guessesAllowed;
        int randomIndex = (int)(Math.random()*(dict.size()));
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
        for(Character ch: list)
        {
            builder.append(ch);
        }
        return builder.toString();
    }

    //shared methods
}
