/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kashenok.rentcar.service;

import com.kashenok.rentcar.entity.Order;
import java.sql.Date;
import java.util.List;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author roma
 */
public class OrderServiceTest {
    
    public OrderServiceTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }

    @Test
    public void testFindAllOrder() throws Exception {
        OrderService instance = new OrderService();
        List<Order> expResult = null;
        List<Order> result = instance.findAllOrder();
        assertNotNull(result);
        System.out.println("List of orders was found");
    }
    
}
