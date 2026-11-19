/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.poechatbox;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;

public class Message {

    private int totalMessagesSent = 0;

    // Part 3 Required Arrays (using ArrayLists for dynamic sizing and safe deletion)
    private ArrayList<String> sentMessages = new ArrayList<>();
    private ArrayList<String> disregardedMessages = new ArrayList<>();
    private ArrayList<String> storedMessages = new ArrayList<>();
    private ArrayList<String> messageHashes = new ArrayList<>();
    private ArrayList<String> messageIDs = new ArrayList<>();

    // Parallel lists to link every message with its sender, recipient, text, and flag
    private ArrayList<String> allSenders = new ArrayList<>();
    private ArrayList<String> allRecipients = new ArrayList<>();
    private ArrayList<String> allMessages = new ArrayList<>();
    private ArrayList<String> allFlags = new ArrayList<>();

    // --- PART 2 METHODS ---

    public String checkRecipientCell(String recipient) {
        if (recipient != null && recipient.startsWith("+") && recipient.length() <= 13 && recipient.length() >= 10) {
            return "Cell phone number successfully captured.";
        }
        return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
    }

    public String checkMessageLength(String message) {
        if (message == null) {
            return "Message cannot be empty.";
        }
        if (message.length() <= 250) {
            return "Message ready to send.";
        }
        int excess = message.length() - 250;
        return "Message exceeds 250 characters by " + excess + ", please reduce size.";
    }

    // Generates a 10-digit Message ID using string manipulation instead of String.format
    public String generateMessageID() {
        Random random = new Random();
        StringBuilder id = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            id.append(random.nextInt(10));
        }
        return id.toString();
    }

    // Strips punctuation and formats hash as XX:0:FIRSTLAST
    public String createMessageHash(String messageID, int messageNumber, String messageText) {
        String firstTwo = (messageID != null && messageID.length() >= 2) ? messageID.substring(0, 2) : "00";
        String cleaned = messageText.replaceAll("[^a-zA-Z0-9\\s]", "").trim();
        String[] words = cleaned.split("\\s+");

        String firstWord = words.length > 0 ? words[0] : "";
        String lastWord = words.length > 1 ? words[words.length - 1] : firstWord;

        return (firstTwo + ":" + messageNumber + ":" + firstWord + lastWord).toUpperCase();
    }

    public String SentMessage(int action) {
        switch (action) {
            case 1:
                totalMessagesSent++;
                return "Message successfully sent.";
            case 2:
                return "Press 0 to delete message.";
            case 3:
                return "Message successfully stored.";
            default:
                return "Invalid selection.";
        }
    }

    public int returnTotalMessagess() {
        return totalMessagesSent;
    }

    /*
     * Stores message details into a valid JSON array inside messages.json.
     * File I/O and JSON array formatting adapted from standard Java NIO file handling
     * documentation (Oracle, 2024. java.nio.file.Files) and W3Schools Java File Handling.
     */
    public void storeMessage(String messageID, String messageHash, String recipient, String messageText) {
        String safeMessage = messageText.replace("\"", "\\\"");
        String jsonObject = "  {\n"
                + "    \"messageID\": \"" + messageID + "\",\n"
                + "    \"messageHash\": \"" + messageHash + "\",\n"
                + "    \"recipient\": \"" + recipient + "\",\n"
                + "    \"message\": \"" + safeMessage + "\"\n"
                + "  }";

        File file = new File("messages.json");
        try {
            if (!file.exists() || file.length() == 0) {
                try (FileWriter writer = new FileWriter(file)) {
                    writer.write("[\n" + jsonObject + "\n]");
                }
            } else {
                String content = new String(Files.readAllBytes(Paths.get("messages.json"))).trim();
                if (content.endsWith("]")) {
                    content = content.substring(0, content.length() - 1).trim();
                    String updated = content.equals("[")
                            ? "[\n" + jsonObject + "\n]"
                            : content + ",\n" + jsonObject + "\n]";
                    try (FileWriter writer = new FileWriter(file)) {
                        writer.write(updated);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error storing message to JSON: " + e.getMessage());
        }
    }

    // --- PART 3: ARRAY POPULATION & JSON READING ---

    public void addMessageRecord(String sender, String recipient, String messageText, String flag, String messageID, String messageHash) {
        allSenders.add(sender);
        allRecipients.add(recipient);
        allMessages.add(messageText);
        allFlags.add(flag);
        messageIDs.add(messageID);
        messageHashes.add(messageHash);

        if (flag.equalsIgnoreCase("Sent")) {
            sentMessages.add(messageText);
        } else if (flag.equalsIgnoreCase("Disregard")) {
            disregardedMessages.add(messageText);
        } else if (flag.equalsIgnoreCase("Stored")) {
            storedMessages.add(messageText);
        }
    }

    /*
     * Reads stored messages from messages.json into the storedMessages array.
     * Adapted from Java NIO Files.readAllBytes documentation (Oracle, 2024).
     */
    public void loadStoredMessagesFromJSON(String defaultSender) {
        File file = new File("messages.json");
        if (!file.exists() || file.length() == 0) {
            return;
        }
        try {
            String content = new String(Files.readAllBytes(Paths.get("messages.json"))).trim();
            String[] objects = content.split("\\{");
            for (int i = 1; i < objects.length; i++) {
                String obj = objects[i];
                String id = extractJsonValue(obj, "messageID");
                String hash = extractJsonValue(obj, "messageHash");
                String recipient = extractJsonValue(obj, "recipient");
                String msg = extractJsonValue(obj, "message");

                if (!id.isEmpty() && !messageIDs.contains(id)) {
                    addMessageRecord(defaultSender, recipient, msg, "Stored", id, hash);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading messages.json: " + e.getMessage());
        }
    }

    private String extractJsonValue(String jsonBlock, String key) {
        String search = "\"" + key + "\": \"";
        int start = jsonBlock.indexOf(search);
        if (start == -1) {
            return "";
        }
        start += search.length();
        int end = jsonBlock.indexOf("\"", start);
        if (end == -1) {
            return "";
        }
        return jsonBlock.substring(start, end);
    }

    // Getters for arrays
    public ArrayList<String> getSentMessages() {
        return sentMessages;
    }

    public ArrayList<String> getDisregardedMessages() {
        return disregardedMessages;
    }

    public ArrayList<String> getStoredMessages() {
        return storedMessages;
    }

    public ArrayList<String> getMessageHashes() {
        return messageHashes;
    }

    public ArrayList<String> getMessageIDs() {
        return messageIDs;
    }

    // --- PART 3: MENU & REPORT METHODS ---

    // 2a. Display sender and recipient of all stored messages
    public String displaySenderAndRecipient() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < allMessages.size(); i++) {
            if (allFlags.get(i).equalsIgnoreCase("Stored") || allFlags.get(i).equalsIgnoreCase("Sent")) {
                sb.append("Sender: ").append(allSenders.get(i))
                  .append(" | Recipient: ").append(allRecipients.get(i)).append("\n");
            }
        }
        return sb.length() > 0 ? sb.toString().trim() : "No messages found.";
    }

    // 2b. Display the longest message
    public String getLongestMessage() {
        String longest = "";
        for (String msg : allMessages) {
            if (msg.length() > longest.length()) {
                longest = msg;
            }
        }
        return longest.isEmpty() ? "No messages found." : longest;
    }

    // 2c. Search for a message ID and display corresponding recipient and message
    public String searchByMessageID(String searchID) {
        for (int i = 0; i < messageIDs.size(); i++) {
            if (messageIDs.get(i).equals(searchID)) {
                return "Recipient: " + allRecipients.get(i) + "\nMessage: " + allMessages.get(i);
            }
        }
        return "Message ID not found.";
    }

    // 2d. Search for all messages sent or stored for a particular recipient
    public String searchByRecipient(String recipient) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < allRecipients.size(); i++) {
            if (allRecipients.get(i).equals(recipient)
                    && (allFlags.get(i).equalsIgnoreCase("Sent") || allFlags.get(i).equalsIgnoreCase("Stored"))) {
                if (sb.length() > 0) {
                    sb.append("\n");
                }
                sb.append(allMessages.get(i));
            }
        }
        return sb.length() > 0 ? sb.toString() : "No messages found for recipient: " + recipient;
    }

    // 2e. Delete a message using the message hash
    public String deleteByMessageHash(String hash) {
        for (int i = 0; i < messageHashes.size(); i++) {
            if (messageHashes.get(i).equalsIgnoreCase(hash)) {
                String deletedMsg = allMessages.get(i);
                String flag = allFlags.get(i);

                if (flag.equalsIgnoreCase("Sent")) {
                    sentMessages.remove(deletedMsg);
                } else if (flag.equalsIgnoreCase("Disregard")) {
                    disregardedMessages.remove(deletedMsg);
                } else if (flag.equalsIgnoreCase("Stored")) {
                    storedMessages.remove(deletedMsg);
                }

                allSenders.remove(i);
                allRecipients.remove(i);
                allMessages.remove(i);
                allFlags.remove(i);
                messageIDs.remove(i);
                messageHashes.remove(i);

                return "Message \"" + deletedMsg + "\" successfully deleted.";
            }
        }
        return "Message hash not found.";
    }

    // 2f. Display a report listing full details of all sent/stored messages
    public String displayReport() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < allMessages.size(); i++) {
            if (allFlags.get(i).equalsIgnoreCase("Sent") || allFlags.get(i).equalsIgnoreCase("Stored")) {
                sb.append("Message Hash: ").append(messageHashes.get(i)).append("\n")
                  .append("Recipient: ").append(allRecipients.get(i)).append("\n")
                  .append("Message: ").append(allMessages.get(i)).append("\n")
                  .append("-------------------------\n");
            }
        }
        return sb.length() > 0 ? sb.toString().trim() : "No messages to report.";
    }
}