package com.launchcode.java;

import java.io.IOException;
import java.util.Scanner;

public class HangmanApplication {

    public static void main(String[] args)throws IOException {
        //how to play the game
        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome to Hangman!");
        System.out.println("I'll choose a word for you " +
                "to try and guess letter by letter.");
        System.out.println("If you guess wrong ten times, " +
                "then you lose.");
        System.out.println("Are you ready?");
        System.out.println();
        System.out.println("I have chosen a word.");
        System.out.println("Below is a picture.");
        System.out.println("Below the picture is your " +
                "current guess.");
        System.out.println("Each incorrect guess results " +
                "in the addition of a body part.");
        System.out.println("When there is an entire person, " +
                "it means you lost the game.");
        //allows for multiple games
        boolean wannaPlay = true;
        while (wannaPlay){
            //setting up the game
            System.out.println("Let's play!!!! :-D");
            Hangman joue = new Hangman();
            do {
                //draw the picture
                System.out.println();
                System.out.println(joue.drawPic());
                System.out.println();
                System.out.println(joue.getOfficialCurrentGuess());
                System.out.println(joue.secretWord); //remove in order to have
                System.out.println();               //to figure out word yourself

                //obtain the guess

                System.out.println("Enter a letter that you believe is");
                System.out.println("likely to be in the word.");
                char guess = (sc.next().toLowerCase()).charAt(0);
                System.out.println();

                //verify originality of the guess

                while (joue.alreadyGuessed(guess)){
                    System.out.println("You've guessed that letter already.");
                    System.out.println("So now try a different letter.");
                    guess = (sc.next()).toLowerCase().charAt(0);
                }   //take a guess
                if (joue.playGuess(guess)){
                    System.out.println("It's a really great guess!  That letter is in the word!!  :-D");
                } else {
                    System.out.println("Sorry, but that letter's not in the word.");
                }

            }
            while (!joue.gameOver());  //continue playing til game is over

            //play it again?
            System.out.println();
            System.out.println("Play again? Enter 'Y' for yes");
            System.out.println("Enter anything else for no");
            Character response = sc.next().toUpperCase().charAt(0);
            wannaPlay = (response == 'Y');
        }
    }
}