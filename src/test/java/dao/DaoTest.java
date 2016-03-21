package dao;

import base.BaseTest;
import dao.h2.H2Database;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.mockito.Mock;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

@Ignore
class DaoTest extends BaseTest{

    @Mock
    protected H2Database mockH2Database;

    @Mock
    protected Connection mockConnection;

    @Mock
    protected PreparedStatement mockStatement;

    @Mock
    protected ResultSet mockResultSet;

    @Before
    public void setUp() throws SQLException {
        when(mockH2Database.getConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockConnection.createStatement()).thenReturn(mockStatement);
        when(mockStatement.getGeneratedKeys()).thenReturn(mockResultSet);
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet);
    }

    @After
    public void tearDown() {
        reset(mockH2Database);
        reset(mockConnection);
        reset(mockStatement);
        reset(mockResultSet);
    }

}
