/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kashenok.rentcar.database.pool;

import java.sql.Connection;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author roma
 */
public class ConnectionPoolTest {
    
    public ConnectionPoolTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }

    @Test
    public void testGetInstance() {
        ConnectionPool expResult = null;
        ConnectionPool result = ConnectionPool.getInstance();
        assertNotEquals(expResult, result);
        System.out.println("ConnectionPool.getInstance() works correct");
    }

    @Test
    public void testGetConnection() {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection expResult = null;
        Connection result = pool.getConnection();
        assertNotEquals(expResult, result);
        System.out.println("Connection created");

    }
}
