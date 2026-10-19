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
 * @author Student
 */


public class LoginTest {
    private Login login;

    @Before 
    public void setUp() {
        login = new Login();
        login.registerUser("kyl_1", "Ch&&sec@ke99!", "Kyle", "Doe");
    }

    // --- USERNAME TESTS (3 Tests) ---
    @Test
    public void testCheckUserNameCorrect() {
        assertTrue(login.checkUserName("kyl_1"));
    }

    @Test
    public void testCheckUserNameMissingUnderscore() {
        assertFalse(login.checkUserName("kyle1")); 
    }

    @Test
    public void testCheckUserNameTooLong() {
        assertFalse(login.checkUserName("kyle_1")); 
    }

    // --- PASSWORD COMPLEXITY TESTS (5 Tests) ---
    @Test
    public void testCheckPasswordComplexityCorrect() {
        assertTrue(login.checkPasswordComplexity("Ch&&sec@ke99!"));
    }

    @Test
    public void testCheckPasswordComplexityTooShort() {
        assertFalse(login.checkPasswordComplexity("A1*abcd")); // Only 7 characters
    }

    @Test
    public void testCheckPasswordComplexityNoCapital() {
        assertFalse(login.checkPasswordComplexity("ch&&sec@ke99!")); 
    }

    @Test
    public void testCheckPasswordComplexityNoNumber() {
        assertFalse(login.checkPasswordComplexity("Ch&&sec@key!")); 
    }

    @Test
    public void testCheckPasswordComplexityNoSpecialChar() {
        assertFalse(login.checkPasswordComplexity("Chsecake9999")); 
    }

    // --- CELL PHONE TESTS (2 Tests) ---
    @Test
    public void testCheckCellPhoneNumberCorrect() {
        assertTrue(login.checkCellPhoneNumber("+27838968976"));
    }

    @Test
    public void testCheckCellPhoneNumberMissingInternationalCode() {
        assertFalse(login.checkCellPhoneNumber("0838968976"));
    }

    // --- LOGIN VALIDATION TESTS (2 Tests) ---
    @Test
    public void testLoginSuccessful() {
        assertTrue(login.loginUser("kyl_1", "Ch&&sec@ke99!"));
    }

    @Test
    public void testLoginFailedWrongPassword() {
        assertFalse(login.loginUser("kyl_1", "wrongpassword"));
    }

    // --- LOGIN STATUS MESSAGING TESTS (2 Tests) ---
    @Test
    public void testReturnLoginStatusSuccess() {
        boolean status = login.loginUser("kyl_1", "Ch&&sec@ke99!");
        assertEquals("Welcome Kyle, Doe it is great to see you again.", login.returnLoginStatus(status));
    }

    @Test
    public void testReturnLoginStatusFailure() {
        boolean status = login.loginUser("kyl_1", "wrongpassword");
        assertEquals("Username or password incorrect, please try again.", login.returnLoginStatus(status));
    }
}