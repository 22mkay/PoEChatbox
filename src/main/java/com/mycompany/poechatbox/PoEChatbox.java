/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.poechatbox;

import java.util.Scanner;

public class PoEChatbox {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Login loginSystem = new Login();
        Message messageSystem = new Message();

        System.out.println("--- Register a New Account ---");
        System.out.print("Enter first name: ");
        String firstName = scanner.nextLine();

        System.out.print("Enter last name: ");
        String lastName = scanner.nextLine();

        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        System.out.print("Enter cell phone number (e.g., +27838968976): ");
        String cellNumber = scanner.nextLine();

        if (!loginSystem.checkCellPhoneNumber(cellNumber)) {
            System.out.println("Cell phone number incorrectly formatted or does not contain international code.");
        } else {
            System.out.println("Cell phone number successfully added.");
        }

        String registrationResult = loginSystem.registerUser(username, password, firstName, lastName);
        System.out.println(registrationResult);

        if (registrationResult.contains("successfully")) {
            System.out.println("\n--- Login to Your Account ---");
            System.out.print("Enter username: ");
            String loginUser = scanner.nextLine();

            System.out.print("Enter password: ");
            String loginPass = scanner.nextLine();

            boolean isMatch = loginSystem.loginUser(loginUser, loginPass);
            System.out.println(loginSystem.returnLoginStatus(isMatch));

            if (isMatch) {
                String senderName = firstName + " " + lastName;

                // Load any previously stored messages from messages.json into the arrays
                messageSystem.loadStoredMessagesFromJSON(senderName);

                boolean running = true;
                while (running) {
                    System.out.println("\nWelcome to QuickChat.");
                    System.out.println("1) Send Messages");
                    System.out.println("2) Show recently sent messages");
                    System.out.println("3) Quit");
                    System.out.println("4) Stored Messages");
                    System.out.print("Choose an option: ");

                    int menuChoice = scanner.nextInt();
                    scanner.nextLine();

                    if (menuChoice == 1) {
                        System.out.print("How many messages do you wish to enter? ");
                        int numMessages = scanner.nextInt();
                        scanner.nextLine();

                        // Number messages starting from 0
                        for (int i = 0; i < numMessages; i++) {
                            System.out.println("\n--- Message " + i + " ---");

                            // Keep asking until recipient cell is valid
                            String recNumber;
                            String recResult;
                            do {
                                System.out.print("Enter recipient cell number: ");
                                recNumber = scanner.nextLine();
                                recResult = messageSystem.checkRecipientCell(recNumber);
                                System.out.println(recResult);
                            } while (!recResult.contains("successfully"));

                            // Keep asking until message length is valid
                            String msgText;
                            String lenResult;
                            do {
                                System.out.print("Enter your message (max 250 characters): ");
                                msgText = scanner.nextLine();
                                lenResult = messageSystem.checkMessageLength(msgText);
                                System.out.println(lenResult);
                            } while (!lenResult.equals("Message ready to send."));

                            // Generate ID and Hash
                            String msgID = messageSystem.generateMessageID();
                            String msgHash = messageSystem.createMessageHash(msgID, i, msgText);

                            // Action Menu
                            System.out.println("\nOptions: 1) Send  2) Disregard  3) Store");
                            System.out.print("Selection: ");
                            int action = scanner.nextInt();
                            scanner.nextLine();

                            System.out.println(messageSystem.SentMessage(action));

                            if (action == 1) {
                                messageSystem.addMessageRecord(senderName, recNumber, msgText, "Sent", msgID, msgHash);
                                System.out.println("\nMessage Details:");
                                System.out.println("Message ID: " + msgID);
                                System.out.println("Message Hash: " + msgHash);
                                System.out.println("Recipient: " + recNumber);
                                System.out.println("Message: " + msgText);
                            } else if (action == 2) {
                                messageSystem.addMessageRecord(senderName, recNumber, msgText, "Disregard", msgID, msgHash);
                            } else if (action == 3) {
                                messageSystem.storeMessage(msgID, msgHash, recNumber, msgText);
                                messageSystem.addMessageRecord(senderName, recNumber, msgText, "Stored", msgID, msgHash);
                            }
                        }
                        System.out.println("\nTotal messages successfully sent: " + messageSystem.returnTotalMessagess());

                    } else if (menuChoice == 2) {
                        System.out.println("Coming Soon.");

                    } else if (menuChoice == 3) {
                        running = false;
                        System.out.println("Exiting QuickChat...");

                    } else if (menuChoice == 4) {
                        boolean inStoredMenu = true;
                        while (inStoredMenu) {
                            System.out.println("\n--- Stored Messages Menu ---");
                            System.out.println("a) Display sender and recipient of all stored messages");
                            System.out.println("b) Display the longest stored message");
                            System.out.println("c) Search for a message ID");
                            System.out.println("d) Search for all messages for a particular recipient");
                            System.out.println("e) Delete a message using the message hash");
                            System.out.println("f) Display full report of all stored messages");
                            System.out.println("g) Back to Main Menu");
                            System.out.print("Choose an option: ");

                            String subChoice = scanner.nextLine().trim().toLowerCase();

                            switch (subChoice) {
                                case "a":
                                    System.out.println("\n" + messageSystem.displaySenderAndRecipient());
                                    break;
                                case "b":
                                    System.out.println("\nLongest Message: " + messageSystem.getLongestMessage());
                                    break;
                                case "c":
                                    System.out.print("Enter Message ID to search: ");
                                    String searchID = scanner.nextLine();
                                    System.out.println("\n" + messageSystem.searchByMessageID(searchID));
                                    break;
                                case "d":
                                    System.out.print("Enter Recipient cell number to search: ");
                                    String searchRec = scanner.nextLine();
                                    System.out.println("\n" + messageSystem.searchByRecipient(searchRec));
                                    break;
                                case "e":
                                    System.out.print("Enter Message Hash to delete: ");
                                    String deleteHash = scanner.nextLine();
                                    System.out.println("\n" + messageSystem.deleteByMessageHash(deleteHash));
                                    break;
                                case "f":
                                    System.out.println("\n--- Full Message Report ---");
                                    System.out.println(messageSystem.displayReport());
                                    break;
                                case "g":
                                    inStoredMenu = false;
                                    break;
                                default:
                                    System.out.println("Invalid option. Please choose a through g.");
                            }
                        }

                    } else {
                        System.out.println("Invalid option.");
                    }
                }
            }
        }
        scanner.close();
    }
}