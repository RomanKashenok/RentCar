package com.kashenok.rentcar.util;

import java.sql.Date;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author roma
 */
public class DateCreatorSQLTest {

    public DateCreatorSQLTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Test
    public void testCreateDate() {
        String year = "2016";
        String month = "04";
        String day = "13";
        Date expResult = Date.valueOf("2016-04-13");
        Date result = DateCreatorSQL.createDate(year, month, day);
        assertEquals(expResult, result);
        System.out.println("Dates are qeuals");

    }

}
