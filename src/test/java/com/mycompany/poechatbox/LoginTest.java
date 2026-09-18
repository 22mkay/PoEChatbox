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

    @Before // Notice this is @Before in JUnit 4, not @BeforeEach
    public void setUp() {
        login = new Login();
        login.registerUser("kyl_1", "Ch&&sec@ke99!", "Kyle", "Doe");
    }

    @Test
    public void testCheckUserNameCorrect() {
        assertTrue(login.checkUserName("kyl_1"));
    }
    
    
}