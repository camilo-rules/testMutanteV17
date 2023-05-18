package com.mutantes.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class DBConnectionTest {
    private DBConnection dbConnection;

    @Before
    public void setUp() {
        dbConnection = new DBConnection();
    }

    @After
    public void tearDown() {
        dbConnection.closeConnection();
    }

    @Test
    public void testConnectionEstablishedSuccessfully() {
        Connection connection = dbConnection.getConnection();

        assertNotNull("Connection should not be null", connection);
        try {
            assertFalse("Connection should be closed", connection.isClosed());
        } catch (SQLException e) {
            fail("An exception occurred while checking connection status: " + e.getMessage());
        }
    }

    @Test
    public void testConnectionClosedSuccessfully() {
        Connection connection = dbConnection.getConnection();
        dbConnection.closeConnection();

        try {
            assertTrue("Connection should be closed", connection.isClosed());
            assertNull("Connection should be null", dbConnection.getConnection());
        } catch (SQLException e) {
            fail("An exception occurred while checking connection status: " + e.getMessage());
        }
    }

}
