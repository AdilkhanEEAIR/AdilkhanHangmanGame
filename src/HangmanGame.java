import java.util.Scanner;
import java.io.FileReader;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.io.IOException;
public class HangmanGame {
    String wordToGuess;
    StringBuilder currentGuess;
    int lives;
    static final int MAX_LIVES = 6;
    public HangmanGame() {
        wordToGuess = getRandomWord();
        currentGuess = new StringBuilder("_".repeat(wordToGuess.length()));
        lives = MAX_LIVES;
    }
    public void startGame() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to Hangman!");
        System.out.println("The word has " + wordToGuess.length() + " letters.");
        System.out.println("You have " + lives + " lives left.");
        while (lives > 0 && currentGuess.toString().contains("_")) {
            System.out.println("\nCurrent word: " + currentGuess);
            System.out.println("Lives remaining: " + lives);
            System.out.print("Enter a letter or the whole word: ");
            String input = scanner.nextLine().toLowerCase();
            if (input.length() == 1) {
                char guessedLetter = input.charAt(0);
                processGuessLetter(guessedLetter);
            } else if (input.length() == wordToGuess.length()) {
                processGuessWord(input);
            } else {
                System.out.println("Invalid input. Please enter letter or the word.");
            }
        }
        if (lives == 0) {
            System.out.println("Game over! The word was: " + wordToGuess);
        } else if (!currentGuess.toString().contains("_")) {
            System.out.println("Congratulations! You guessed the word: " + wordToGuess);
        }
    }
    public void processGuessLetter(char letter) {
        int index = wordToGuess.indexOf(letter);
        if (index != -1) {
            while (index != -1) {
                currentGuess.setCharAt(index, letter);
                index = wordToGuess.indexOf(letter, index + 1);
            }
            System.out.println("Correct! Word now is: " + currentGuess);
        } else {
            lives--;
            System.out.println("Incorrect! Lives remaining: " + lives);
        }
    }
    public void processGuessWord(String word) {
        if (word.equals(wordToGuess)) {
            currentGuess = new StringBuilder(wordToGuess);
            System.out.println("Congratulations! You guessed the word: " + wordToGuess);
        } else {
            lives--;
            System.out.println("Incorrect word! Lives remaining: " + lives);
        }
    }
    public String getRandomWord() {
        List<String> words = loadWordsFromFile("src/words.txt");
        Random random = new Random();
        return words.get(random.nextInt(words.size()));
    }
    public List<String> loadWordsFromFile(String filePath) {
        List<String> words = new ArrayList<>();
        File file = new File(filePath);
        Scanner fileScanner = null;
        try {
            fileScanner = new Scanner(file);
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine().trim().toLowerCase();
                if (!line.isEmpty()) {
                    words.add(line);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading the file. Please ensure 'words.txt' exists.");
        } finally {
            if (fileScanner != null) {
                fileScanner.close();
            }
        }
        return words;
    }
    public static void main(String[] args) {
        HangmanGame game = new HangmanGame();
        game.startGame();
    }
}