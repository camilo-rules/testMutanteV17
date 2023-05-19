package com.mutantes.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.mockito.Mockito.*;

public class StatusHandlerTest {

    private StatusHandler statusHandler;
    private DBConnection mockDbConnection;
    private Connection mockConnection;
    private PreparedStatement mockStatement;
    private ResultSet mockResultSet;

    @Before
    public void setup() throws SQLException {
        mockDbConnection = mock(DBConnection.class);
        mockConnection = mock(Connection.class);
        mockStatement = mock(PreparedStatement.class);
        mockResultSet = mock(ResultSet.class);

        statusHandler = new StatusHandler();
        statusHandler.setDbConnection(mockDbConnection);

        when(mockDbConnection.getConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true).thenReturn(false);
        when(mockResultSet.getInt(1)).thenReturn(10);
    }

    @Test
    public void testHandleRequest() throws SQLException {
        ApiResponse response = statusHandler.handleRequest(null, null);

        verify(mockStatement, times(2)).setBoolean(eq(1), anyBoolean());
        verify(mockStatement, times(2)).executeQuery();
        verify(mockResultSet, times(2)).next();
        verify(mockResultSet, times(1)).getInt(1);

        assertEquals(200, response.getStatusCode());
        assertNotNull(response.getResponse());
        // Add additional assertions for the response if needed
    }

    @Test
    public void testCalculateRatio() {
        double ratio1 = statusHandler.calculateRatio(10, 5);
        assertEquals(2.0, ratio1, 0.0);

        double ratio2 = statusHandler.calculateRatio(10, 0);
        assertEquals(10.0, ratio2, 0.0);
    }
}
