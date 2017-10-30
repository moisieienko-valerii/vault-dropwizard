package mvp.vault.demo.health;

import com.codahale.metrics.health.HealthCheck;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import java.sql.Connection;
import java.sql.Statement;

/**
 *
 */
public class DatabaseHealthCheck extends HealthCheck {
    private final MysqlDataSource database;

    public DatabaseHealthCheck(MysqlDataSource database) {
        this.database = database;
    }

    @Override
    protected Result check() throws Exception {
        if (isConnected()) {
            return Result.healthy();
        }
        else {
            return Result.unhealthy("Cannot connect to [" + this.database.getUrl() + "] " +
                    "with user [" + this.database.getUser() + "]");
        }
    }

    private boolean isConnected() {
        try (Connection connection = this.database.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute("SELECT 1");
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }
}
