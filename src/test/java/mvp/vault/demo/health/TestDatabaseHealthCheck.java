package mvp.vault.demo.health;

import com.codahale.metrics.health.HealthCheck;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import org.easymock.EasyMockSupport;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static mvp.vault.demo.health.DatabaseHealthCheck.CHECK_QUERY;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Tests for {@link DatabaseHealthCheck}
 */
public class TestDatabaseHealthCheck extends EasyMockSupport {
    @Rule
    public ExpectedException testRuleExpectedException = ExpectedException.none();

    private DatabaseHealthCheck check;
    private MysqlDataSource dataSourceMock;
    private Statement statementMock;

    @Before
    public void prepare() throws SQLException {
        this.dataSourceMock = createMock(MysqlDataSource.class);

        Connection connectionMock = createMock(Connection.class);
        expect(this.dataSourceMock.getConnection()).andReturn(connectionMock);

        this.statementMock = createMock(Statement.class);
        expect(connectionMock.createStatement()).andReturn(this.statementMock);

        this.statementMock.close();
        expectLastCall();

        connectionMock.close();
        expectLastCall();

        this.check = new DatabaseHealthCheck(dataSourceMock);
    }

    @Test
    public void testCreationWithNullDataSource() {
        this.testRuleExpectedException.expect(NullPointerException.class);

        new DatabaseHealthCheck(null);
    }

    @Test
    public void testSuccessfulCheck() throws Exception {
        expect(this.statementMock.execute(CHECK_QUERY)).andReturn(Boolean.TRUE);
        replayAll();
        HealthCheck.Result result = this.check.check();

        assertTrue("expects healthy database connection", result.isHealthy());
        verifyAll();
    }

    @Test
    public void testUnsuccessfulCheck() throws Exception {
        String url = "localhost";
        String username = "user";

        expect(this.dataSourceMock.getUrl()).andReturn(url);
        expect(this.dataSourceMock.getUser()).andReturn(username);
        expect(this.statementMock.execute(CHECK_QUERY)).andThrow(new SQLException("Everything is bad!"));

        replayAll();
        HealthCheck.Result result = this.check.check();

        assertFalse("expects healthy database connection", result.isHealthy());
        assertEquals("Cannot connect to [" + url + "] with user [" + username + "]", result.getMessage());
        verifyAll();
    }

}
