package com.launchcode.java;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Hangman {
    String secretWord;
    StringBuilder currentGuess; //stringbuilder allows a string that can be changed
    ArrayList<Character> priorGuesses = new ArrayList<>();

    int maxTries = 11;
    int currentTry = 0;

    ArrayList<String> dictionary = new ArrayList<>();
    private static FileReader fileReader;
    //buffered file reader allows iteration through a file
    //and for adding items to / taking items from a file
    private static BufferedReader bufferedFileReader;

    public Hangman() throws IOException { //throw exc in case dictionary
        initializeStreams();                //  file is not there
        //initialize stream initialized dictionary word list
        secretWord = chooseWord();
        currentGuess = initializeCurrentGuess();

    }
    public void initializeStreams()throws IOException {
        try {
            File inFile = new File("dictionary.txt");
            fileReader = new FileReader(inFile);
            bufferedFileReader = new BufferedReader(fileReader);
            String currentLine = bufferedFileReader.readLine();    //gets first line of doc
            while (currentLine != null){
                dictionary.add(currentLine);                  // add the current line
                currentLine = bufferedFileReader.readLine();  //increment current line;
                                    //allows to go to the next line
            }
            bufferedFileReader.close();
            fileReader.close();
        } catch (IOException e){
            System.out.println("Could not initialize streams.");
        }
    }
    public String chooseWord(){
        Random rn = new Random();
        int wordIndex = Math.abs(rn.nextInt()) % dictionary.size();
        return dictionary.get(wordIndex);
    }
    public StringBuilder initializeCurrentGuess(){
        StringBuilder current = new StringBuilder();
        for (int i = 0; i < secretWord.length() * 2; i += 1){
            // * 2 since word will be twice as long when considering spaces
            if (i % 2 == 0){
                current.append("_");
            } else {
                current.append(" ");
            }
        }
        return current;  //current = StringBuilder variable
    }
        // _ _ A _ _ _ _ B _ _
    public String getOfficialCurrentGuess(){
        return "Current Guess: " + currentGuess.toString();
    }

    public boolean gameOver(){
        if (won()){
            System.out.println();
            System.out.println("Congratulations!! You guessed the word!! :-D ");
            return true;
        } else if (lost()){
            System.out.println();
            System.out.println("Sorry, but you lose.");
            System.out.println("If you feel like doing " +
                    "it, maybe can give it another go.");
            System.out.println();
            System.out.println("In the event that you'd like to");
            System.out.println("know it, the word is " + secretWord + ".");
            return true;
        }
        return false;
    }
    public boolean lost(){
        return currentTry >= maxTries;
    }
    // _ _ _ A _ _ _ _ B _ _
    public boolean won(){
        String guess = getCondensedCurrentGuess();
        return guess.equals(secretWord);
    }
    public String getCondensedCurrentGuess(){
        String guess = currentGuess.toString();
        return guess.replace(" ", "");
    }

    // " - - - - - -\n"+
    // " |        | \n"+
    // " |        0 \n" +
    // " |      / |\\ \n"+
    // " |     /  | \ \n" +
    // " |       / \\ \n"
    // " |\n" + /  \ \n" +
    //    // " |\n" +
    //    // " |\n" +
    //    // " |\n";

    public boolean alreadyGuessed(char guess){
        return priorGuesses.contains(guess);
    }
    public boolean playGuess(char guess){
        boolean goodGuess = false;
        priorGuesses.add(guess);
        for (int b = 0; b < secretWord.length(); b += 1){
            if (secretWord.charAt(b) == guess){
                currentGuess.setCharAt(b * 2, guess); //can only do w/ Stringbuilders
                goodGuess = true;
            }
        }
        if (goodGuess){
            currentTry++;
        }
        return goodGuess;
    }

    public String drawPic(){
        switch (currentTry){
            case 0: return noPersonDraw();
            case 1: return addHeadDraw();
            case 2: return addBodyDraw();
            case 3: return addUpperRightArmDraw();
            case 4: return addUpperLeftArmDraw();
            case 5: return addLowerRightArmDraw();
            case 6: return addLowerLeftArmDraw();
            case 7: return addUpperRightLegDraw();
            case 8: return addUpperLeftLegDraw();
            case 9: return addLowerRightLegDraw();
            default: return fullPersonDraw();
        }
    }
    private String noPersonDraw(){
        return      " - - - - - -\n"+
                    " |        | \n"+
                    " |        \n" +
                    " |      \n"+
                    " |     \n" +
                    " |       \n" +
                    " |      \n" +
                    " |\n" +
                    " |\n" +
                    " |\n";

    }
    private String addHeadDraw(){
        return      " - - - - - -\n"+
                " |        | \n"+
                " |        0     \n" +
                " |      \n"+
                " |     \n" +
                " |       \n" +
                " |      \n" +
                " |\n" +
                " |\n" +
                " |\n";
    }
    private String addBodyDraw(){
        return      " - - - - - -\n"+
                " |        | \n"+
                " |        0     \n" +
                " |        |\n"+
                " |        | \n" +
                " |       \n" +
                " |      \n" +
                " |\n" +
                " |\n" +
                " |\n";
    }
    private String addUpperRightArmDraw(){
        return      " - - - - - -\n"+
                " |        | \n"+
                " |        0     \n" +
                " |       /|\n"+
                " |        | \n" +
                " |       \n" +
                " |      \n" +
                " |\n" +
                " |\n" +
                " |\n";
    }
    private String addUpperLeftArmDraw(){
        return      " - - - - - -\n"+
                " |        | \n"+
                " |        0     \n" +
                " |       /|\\ \n"+
                " |        | \n" +
                " |       \n" +
                " |      \n" +
                " |\n" +
                " |\n" +
                " |\n";
    }
    private String addLowerRightArmDraw(){
        return      " - - - - - -\n"+
                " |        | \n"+
                " |        0     \n" +
                " |       /|\\ \n"+
                " |      / | \n" +
                " |          \n" +
                " |      \n" +
                " |\n" +
                " |\n" +
                " |\n";
    }
    private String addLowerLeftArmDraw(){
        return      " - - - - - -\n"+
                " |        | \n"+
                " |        0     \n" +
                " |       /|\\ \n"+
                " |      / | \\ \n" +
                " |          \n" +
                " |      \n" +
                " |\n" +
                " |\n" +
                " |\n";
    }
    private String addUpperRightLegDraw(){
        return      " - - - - - -\n"+
                " |        | \n"+
                " |        0     \n" +
                " |       /|\\ \n"+
                " |      / | \\ \n" +
                " |       /   \n" +
                " |      \n" +
                " |\n" +
                " |\n" +
                " |\n";
    }
    private String addUpperLeftLegDraw(){
        return      " - - - - - -\n"+
                " |        | \n"+
                " |        0     \n" +
                " |       /|\\ \n"+
                " |      / | \\ \n" +
                " |       / \\ \n" +
                " |      \n" +
                " |\n" +
                " |\n" +
                " |\n";
    }
    private String addLowerRightLegDraw(){
        return      " - - - - - -\n"+
                " |        | \n"+
                " |        0     \n" +
                " |       /|\\ \n"+
                " |      / | \\ \n" +
                " |       / \\ \n" +
                " |      /      \n" +
                " |\n" +
                " |\n" +
                " |\n";
    }

    private String fullPersonDraw(){
        return      " - - - - - -\n"+
                " |        | \n"+
                " |        0     \n" +
                " |       /|\\ \n"+
                " |      / | \\ \n" +

                " |       / \\ \n" +
                " |      /   \\   \n" +
                " |\n" +
                " |\n" +
                " |\n";
    }
}
