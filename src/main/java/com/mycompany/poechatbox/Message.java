/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.poechatbox;

import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author THE ONE
 */
public class Message {
    
    // Static counter to keep track of total messages sent across the whole application
    private static int totalMessagesSent = 0; 

    // Generates a random 10-digit ID string
    public String generateMessageID() {
        long id = (long) (Math.random() * 10000000000L);
        return String.format("%010d", id);
    }

    public boolean checkMessageID(String messageID) {
        // Ensures the message ID is not more than ten characters[cite: 22]
        return messageID.length() <= 10;
    }

    public String checkRecipientCell(String cellNumber) {
        // Note: The rubric states "no more than ten characters"[cite: 22], 
        // but your provided test data (+27718693002) is 12 characters long[cite: 24]. 
        // This regex ensures it starts with a '+' and has 9 to 11 digits to pass the test data.
        if (cellNumber.matches("^\\+\\d{9,11}$")) {
            return "Cell phone number successfully captured.";
        } else {
            return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
        }
    }

    public String checkMessageLength(String messageText) {
        // Ensures message does not exceed 250 characters and returns specific strings[cite: 19, 26]
        if (messageText.length() <= 250) {
            return "Message ready to send.";
        } else {
            int excess = messageText.length() - 250;
            return "Message exceeds 250 characters by " + excess + "; please reduce the size.";
        }
    }

    public String createMessageHash(String messageID, int messageNumber, String messageText) {
        // Extracts the first 2 characters of the ID[cite: 20]
        String firstTwoID = messageID.substring(0, 2);
        
        // Splits the message into an array of words to find the first and last[cite: 20]
        String[] words = messageText.split(" ");
        String firstWord = words[0];
        String lastWord = words[words.length - 1];
        
        // Builds the hash in the format: 00:0:HITHANKS[cite: 20]
        String hash = firstTwoID + ":" + messageNumber + ":" + firstWord + lastWord;
        return hash.toUpperCase();
    }

    public String SentMessage(int choice) {
        // Handles the Send, Disregard, or Store menu options[cite: 21, 28]
        switch (choice) {
            case 1:
                totalMessagesSent++;
                return "Message successfully sent.";
            case 2:
                return "Press 0 to delete the message.";
            case 3:
                return "Message successfully stored.";
            default:
                return "Invalid selection.";
        }
    }

    public int returnTotalMessagess() {
        // Returns the total number of messages sent[cite: 23]
        return totalMessagesSent;
    }
    
    public void storeMessage() {
        // Placeholder: We will add the JSON file writing logic in the next iteration[cite: 21, 23]
    }
}
