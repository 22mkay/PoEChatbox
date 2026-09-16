/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.poechatbox;

import java.util.Scanner;
/**
 *
 * @author Student
 */
public class PoEChatbox {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Login loginSystem = new Login();

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

        // Check phone number separately as per the instructions before full registration
        if (!loginSystem.checkCellPhoneNumber(cellNumber)) {
             System.out.println("Cell phone number incorrectly formatted or does not contain international code.");
        } else {
             System.out.println("Cell phone number successfully added.");
        }

        // Attempt Registration
        String registrationResult = loginSystem.registerUser(username, password, firstName, lastName);
        System.out.println(registrationResult);

        // Only proceed to login if registration was successful
        if (registrationResult.contains("successfully")) {
            System.out.println("\n--- Login to Your Account ---");
            System.out.print("Enter username: ");
            String loginUser = scanner.nextLine();
            
            System.out.print("Enter password: ");
            String loginPass = scanner.nextLine();
            
            boolean isMatch = loginSystem.loginUser(loginUser, loginPass);
            System.out.println(loginSystem.returnLoginStatus(isMatch));
        }
        
        scanner.close();
    }
}