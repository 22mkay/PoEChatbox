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
                boolean running = true;
                while (running) {
                    System.out.println("\nWelcome to QuickChat.");
                    System.out.println("1) Send Messages");
                    System.out.println("2) Show recently sent messages");
                    System.out.println("3) Quit");
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

                            // Show full details after the message is sent
                            if (action == 1) {
                                System.out.println("\nMessage Details:");
                                System.out.println("Message ID: " + msgID);
                                System.out.println("Message Hash: " + msgHash);
                                System.out.println("Recipient: " + recNumber);
                                System.out.println("Message: " + msgText);
                            } else if (action == 3) {
                                messageSystem.storeMessage(msgID, msgHash, recNumber, msgText);
                            }
                        }
                        System.out.println("\nTotal messages successfully sent: " + messageSystem.returnTotalMessagess());

                    } else if (menuChoice == 2) {
                        System.out.println("Coming Soon.");
                    } else if (menuChoice == 3) {
                        running = false;
                        System.out.println("Exiting QuickChat...");
                    } else {
                        System.out.println("Invalid option.");
                    }
                }
            }
        }
        scanner.close();
    }
}