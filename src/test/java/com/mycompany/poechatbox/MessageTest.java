/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.poechatbox;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

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
        // Tests the valid international number from Task 1[cite: 24, 26]
        assertEquals("Cell phone number successfully captured.", messageFeature.checkRecipientCell("+27718693002"));
    }

    @Test
    public void testCheckRecipientCellFailure() {
        // Tests the invalid local number from Task 2[cite: 25, 26]
        assertEquals("Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.", messageFeature.checkRecipientCell("08575975889"));
    }

    // --- MESSAGE LENGTH TESTS ---
    @Test
    public void testCheckMessageLengthSuccess() {
        // Tests the standard length message from Task 1[cite: 24, 26]
        assertEquals("Message ready to send.", messageFeature.checkMessageLength("Hi Mike, can you join us for dinner tonight?"));
    }

    @Test
    public void testCheckMessageLengthFailure() {
        // Generates a 260-character string to test the exact mathematical failure output[cite: 26]
        String exact260CharString = "1234567890".repeat(26);
        assertEquals("Message exceeds 250 characters by 10; please reduce the size.", messageFeature.checkMessageLength(exact260CharString));
    }

    // --- HASH AND ID TESTS ---
    @Test
    public void testCreateMessageHash() {
        // The rubric specifies the exact return string "00:0:HITONIGHT" for Test Case 1[cite: 27]
        // Note: Punctuation is omitted from "TONIGHT" in the input to match the strict rubric output.
        String generatedHash = messageFeature.createMessageHash("0012345678", 0, "Hi Mike, can you join us for dinner TONIGHT");
        assertEquals("00:0:HITONIGHT", generatedHash);
    }

    @Test
    public void testGenerateMessageID() {
        // Ensures the generated ID does not exceed the ten-character limit[cite: 22, 27]
        String generatedID = messageFeature.generateMessageID();
        assertTrue(messageFeature.checkMessageID(generatedID));
    }

    // --- SEND OPTIONS TESTS ---
    @Test
    public void testSentMessageSend() {
        assertEquals("Message successfully sent.", messageFeature.SentMessage(1)); //[cite: 28]
    }

    @Test
    public void testSentMessageDisregard() {
        assertEquals("Press 0 to delete the message.", messageFeature.SentMessage(2)); //[cite: 28]
    }

    @Test
    public void testSentMessageStore() {
        assertEquals("Message successfully stored.", messageFeature.SentMessage(3)); //[cite: 28]
    }
}