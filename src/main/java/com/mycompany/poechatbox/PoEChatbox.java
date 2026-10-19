/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.poechatbox;


import java.util.Scanner;

/**
 *
 * @author THE ONE
 */

public class PoEChatbox {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Login loginSystem = new Login();
        Message messageSystem = new Message(); // Initialize the new Message class

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

            // --- PART 2: THE MESSAGE MENU LOOP ---
            if (isMatch) {
                boolean running = true;
                while (running) {
                    System.out.println("\nWelcome to QuickChat.");
                    System.out.println("1) Send Messages");
                    System.out.println("2) Show recently sent messages");
                    System.out.println("3) Quit");
                    System.out.print("Choose an option: ");
                    
                    int menuChoice = scanner.nextInt();
                    scanner.nextLine(); // Consume newline

                    if (menuChoice == 1) {
                        System.out.print("How many messages do you wish to enter? ");
                        int numMessages = scanner.nextInt();
                        scanner.nextLine(); // Consume newline
                        
                        for (int i = 1; i <= numMessages; i++) {
                            System.out.println("\n--- Message " + i + " ---");
                            
                            // Get Recipient
                            System.out.print("Enter recipient cell number: ");
                            String recNumber = scanner.nextLine();
                            System.out.println(messageSystem.checkRecipientCell(recNumber));
                            
                            // Get Message
                            System.out.print("Enter your message (max 250 characters): ");
                            String msgText = scanner.nextLine();
                            System.out.println(messageSystem.checkMessageLength(msgText));
                            
                            // Generate Data
                            String msgID = messageSystem.generateMessageID();
                            String msgHash = messageSystem.createMessageHash(msgID, i, msgText);
                            
                            // Display Full Details
                            System.out.println("\nMessage Details:");
                            System.out.println("Message ID: " + msgID);
                            System.out.println("Message Hash: " + msgHash);
                            System.out.println("Recipient: " + recNumber);
                            System.out.println("Message: " + msgText);
                            
                            // Action Menu
                            System.out.println("\nOptions: 1) Send  2) Disregard  3) Store");
                            System.out.print("Selection: ");
                            int action = scanner.nextInt();
                            scanner.nextLine(); // Consume newline
                            
                            System.out.println(messageSystem.SentMessage(action));
                            
                            if (action == 3) {
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
                        //Final test for GitHub actions
                    }
                }
            }
        }
        scanner.close();
    }
}