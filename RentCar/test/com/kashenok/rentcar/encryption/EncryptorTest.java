/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kashenok.rentcar.encryption;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author roma
 */
public class EncryptorTest {
    
    public EncryptorTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }

    @Test
    public void testEncode() throws Exception {
        String value = "password";
        Encryptor instance = new Encryptor();
        String expResult = "password";
        String result = instance.encode(value);
        assertNotEquals(expResult, result);
        System.out.println("Encryption works");
    }
    
}
