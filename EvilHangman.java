import java.util.*;

public class EvilHangman extends BaseHangman {

    Map<String, List> patternMap = new HashMap<>();
    List<String> narrowDict = new ArrayList<>();

    public EvilHangman(String filename){convertFile(filename);}

    @Override
    public void initGame(int guessesAllowed) {
        commonReset(guessesAllowed);
        //put all words of given length in a narrow dictionary
        for(String element: dict){
            if (element.length() == secretWordLength){
                narrowDict.add(element);
            }
        }
    }

    @Override
    public boolean updateWithGuess(char letter) {

        patternMap.clear();
        //check if not duplicate, don't add if duplicate
        if (!isDuplicate(letter)) {return false;}

        //create a pattern map with every word
        //looping over all words of setLength in narrowed dictionary
        for(String element: narrowDict){
            List<Character> word = new ArrayList<>();
            //loop over every character in each word
            for (int i = 0; i < secretWordLength; i++){
                //create new pattern
                if (element.charAt(i) == letter){
                    word.add(i, letter);
                } else {
                    word.add(i, BLANK);
                }
                //if letter has been guessed before, add it
                for(Character guessedLetter: guessedLetters){
                    if (element.charAt(i) == guessedLetter){
                        word.remove(i);
                        word.add(i, guessedLetter);
                    }
                }
            }
            //associate pattern with element in dictionary
            patternMap.put(element, word);
        }
        //determine the frequency of patterns found by making a frequency map
        Map<List, Integer> frequencyMap = new HashMap<>();
        for (Map.Entry<String, List> entry : patternMap.entrySet()) {
            List value = entry.getValue();
            int count;
            if(frequencyMap.containsKey(value)) {
                count = frequencyMap.get(value) + 1;
            } else { count = 1; }
            frequencyMap.put(value, count);
        }
        //System.out.println("frequencyMap: " + frequencyMap.toString());

        //find the most frequent pattern
        //(if same size, first pattern in map takes precedence)
        List mostFreqPattern = new ArrayList<>();
        boolean firstTime = true;
        for (List pattern : frequencyMap.keySet()) {
            if (firstTime){mostFreqPattern = pattern;}
            firstTime = false;
            if (frequencyMap.get(pattern) > frequencyMap.get(mostFreqPattern)){
                mostFreqPattern = pattern;
            }
        }

        //clear old dictionary for smaller one
        narrowDict.clear();
        //Make a narrower dictionary with only the words
        // that are mapped to the most frequent pattern
        for (Map.Entry<String, List> entry : patternMap.entrySet()) {
            if (entry.getValue().equals(mostFreqPattern)){
                narrowDict.add(entry.getKey());
            }
        }

        //pick new random secret word from narrower dictionary
        int randomIndex = (int)(Math.random()*(narrowDict.size()));
        secretWord = narrowDict.get(randomIndex);

        //If most frequent pattern is no change from before, return false
        //and leave puzzleGuessed unchanged.
        //Else, return true
        //and set puzzleGuessed equal to mostFreqPattern.
        if(mostFreqPattern.equals(puzzleGuessed)){
            guesses--;
            return false;
        } else {
            puzzleGuessed = mostFreqPattern;
            return true;
        }
    }
}
