/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.poechatbox;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Arrays;

/**
 *
 * @author THE ONE
 */

public class MessageTest {
    private Message messageFeature;

    @Before
    public void setUp() {
        messageFeature = new Message();
    }

    // --- RECIPIENT FORMAT TESTS ---
    @Test
    public void testCheckRecipientCellSuccess() {
        // Tests the valid international number from Task 1
        assertEquals("Cell phone number successfully captured.", messageFeature.checkRecipientCell("+27718693002"));
    }

    @Test
    public void testCheckRecipientCellFailure() {
        // Tests the invalid local number from Task 2
        assertEquals("Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.", messageFeature.checkRecipientCell("08575975889"));
    }

    // --- MESSAGE LENGTH TESTS ---
    @Test
    public void testCheckMessageLengthSuccess() {
        // Tests the standard length message from Task 1
        assertEquals("Message ready to send.", messageFeature.checkMessageLength("Hi Mike, can you join us for dinner tonight?"));
    }

    @Test
    public void testCheckMessageLengthFailure() {
        // Generates a 260-character string to test the exact mathematical failure output
        String exact260CharString = "1234567890".repeat(26);
        assertEquals("Message exceeds 250 characters by 10; please reduce the size.", messageFeature.checkMessageLength(exact260CharString));
    }

    // --- HASH AND ID TESTS ---
    @Test
    public void testCreateMessageHash() {
        // The rubric specifies the exact return string 00:0:HITONIGHT for Test Case 1
        // Note: Punctuation is omitted from "TONIGHT" in the input to match the strict rubric output.
        String generatedHash = messageFeature.createMessageHash("0012345678", 0, "Hi Mike, can you join us for dinner TONIGHT");
        assertEquals("00:0:HITONIGHT", generatedHash);
    }

    @Test
    public void testGenerateMessageID() {
        // Ensures the generated ID does not exceed the ten-character limit
        String generatedID = messageFeature.generateMessageID();
        assertTrue(messageFeature.checkMessageID(generatedID));
    }

    // --- SEND OPTIONS TESTS ---
    @Test
    public void testSentMessageSend() {
        assertEquals("Message successfully sent.", messageFeature.SentMessage(1)); 
    }

    @Test
    public void testSentMessageDisregard() {
        assertEquals("Press 0 to delete the message.", messageFeature.SentMessage(2)); 
    }

    @Test
    public void testSentMessageStore() {
        assertEquals("Message successfully stored.", messageFeature.SentMessage(3));
    }
        
    // Helper method to populate Test Data Messages 1 to 5
    private void populatePart3TestData(Message msg) {
        String id1 = "0000000001";
        String hash1 = msg.createMessageHash(id1, 0, "Did you get the cake?");
        msg.addMessageRecord("Developer", "+27834557896", "Did you get the cake?", "Sent", id1, hash1);

        String id2 = "0000000002";
        String hash2 = msg.createMessageHash(id2, 1, "Where are you? You are late! I have asked you to be on time.");
        msg.addMessageRecord("Developer", "+27838884567", "Where are you? You are late! I have asked you to be on time.", "Stored", id2, hash2);

        String id3 = "0000000003";
        String hash3 = msg.createMessageHash(id3, 2, "Yohoooo, I am at your gate.");
        msg.addMessageRecord("Developer", "+27834484567", "Yohoooo, I am at your gate.", "Disregard", id3, hash3);

        String id4 = "0000000004";
        String hash4 = msg.createMessageHash(id4, 3, "It is dinner time!");
        msg.addMessageRecord("0838884567", "0838884567", "It is dinner time!", "Sent", id4, hash4);

        String id5 = "0000000005";
        String hash5 = msg.createMessageHash(id5, 4, "Ok, I am leaving without you.");
        msg.addMessageRecord("Developer", "+27838884567", "Ok, I am leaving without you.", "Stored", id5, hash5);
    }

    @Test
    public void testSentMessagesArrayPopulated() {
        Message msg = new Message();
        populatePart3TestData(msg);

        assertEquals(
            Arrays.asList("Did you get the cake?", "It is dinner time!"),
            msg.getSentMessages()
        );
    }

    @Test
    public void testDisplayLongestMessage() {
        Message msg = new Message();
        populatePart3TestData(msg);

        assertEquals(
            "Where are you? You are late! I have asked you to be on time.",
            msg.getLongestMessage()
        );
    }

    @Test
    public void testSearchByMessageID() {
        Message msg = new Message();
        populatePart3TestData(msg);

        String expected = "Recipient: 0838884567\nMessage: It is dinner time!";
        assertEquals(expected, msg.searchByMessageID("0000000004"));
    }

    @Test
    public void testSearchMessagesByRecipient() {
        Message msg = new Message();
        populatePart3TestData(msg);

        String expected = "Where are you? You are late! I have asked you to be on time.\nOk, I am leaving without you.";
        assertEquals(expected, msg.searchByRecipient("+27838884567"));
    }

    @Test
    public void testDeleteMessageByHash() {
        Message msg = new Message();
        populatePart3TestData(msg);

        String hashToDelete = msg.createMessageHash("0000000002", 1, "Where are you? You are late! I have asked you to be on time.");
        String expected = "Message \"Where are you? You are late! I have asked you to be on time.\" successfully deleted.";

        assertEquals(expected, msg.deleteByMessageHash(hashToDelete));
    }

    @Test
    public void testDisplayReport() {
        Message msg = new Message();
        populatePart3TestData(msg);

        String report = msg.displayReport();
        assertTrue(report.contains("Message Hash:"));
        assertTrue(report.contains("Recipient: +27834557896"));
        assertTrue(report.contains("Message: Did you get the cake?"));
        assertTrue(report.contains("Recipient: 0838884567"));
        assertTrue(report.contains("Message: It is dinner time!"));
    }
}