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

public clas LoginTest {
    private Login login;

    @Before 
    public void setUp() {
        login = new Login();
        login.registerUser("kyl_1", "Ch&&sec@ke99!", "Kyle", "Doe");
    }

    @Test
    public void testCheckUserNameCorrect() {
        assertTrue(login.checkUserName("kyl_1"));
    }
    
  
    @Test
    public void testCheckUserNameIncorrect() {
        assertFalse(login.checkUserName("kyle!!!!!!!"));
    }

    @Test
    public void testCheckPasswordComplexityCorrect() {
        assertTrue(login.checkPasswordComplexity("Ch&&sec@ke99!"));
    }

    @Test
    public void testCheckPasswordComplexityIncorrect() {
        assertFalse(login.checkPasswordComplexity("password"));
    }
    
    @Test
    public void testCheckCellPhoneNumberCorrect() {
        assertTrue(login.checkCellPhoneNumber("+27838968976"));
    }

    @Test
    public void testCheckCellPhoneNumberIncorrect() {
        assertFalse(login.checkCellPhoneNumber("08966553"));
    }

    @Test
    public void testLoginSuccessful() {
        assertTrue(login.loginUser("kyl_1", "Ch&&sec@ke99!"));
    }

    @Test
    public void testLoginFailed() {
        assertFalse(login.loginUser("kyl_1", "wrongpassword"));
    }

    @Test
    public void testReturnLoginStatusSuccess() {
        boolean status = login.loginUser("kyl_1", "Ch&&sec@ke99!");
        assertEquals("Welcome Kyle, Doe it is great to see you again.", login.returnLoginStatus(status));
    }

    
}