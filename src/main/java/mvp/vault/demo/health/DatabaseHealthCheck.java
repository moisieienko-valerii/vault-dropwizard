package mvp.vault.demo.health;

import com.codahale.metrics.health.HealthCheck;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import java.sql.Connection;
import java.sql.Statement;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Health check for Mysql database connection.
 * This implementation uses simple query to identify connection or database issues.
 */
public final class DatabaseHealthCheck extends HealthCheck {
    static final String CHECK_QUERY = "SELECT 1";
    private final MysqlDataSource dataSource;

    public DatabaseHealthCheck(MysqlDataSource dataSource) {
        this.dataSource = checkNotNull(dataSource, "dataSource");
    }

    @Override
    protected Result check() throws Exception {
        if (isConnected()) {
            return Result.healthy();
        }
        else {
            return Result.unhealthy("Cannot connect to [" + this.dataSource.getUrl() + "] " +
                    "with user [" + this.dataSource.getUser() + "]");
        }
    }

    private boolean isConnected() {
        try (Connection connection = this.dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(CHECK_QUERY);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }
}
