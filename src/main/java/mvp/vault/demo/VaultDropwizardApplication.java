package mvp.vault.demo;

import com.mysql.jdbc.jdbc2.optional.MysqlConnectionPoolDataSource;
import io.dropwizard.Application;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import mvp.vault.demo.health.DatabaseHealthCheck;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VaultDropwizardApplication extends Application<VaultDropwizardConfiguration> {
    private static final Logger LOG = LoggerFactory.getLogger(VaultDropwizardApplication.class);

    public static void main(final String[] args) throws Exception {
        new VaultDropwizardApplication().run(args);
    }

    @Override
    public String getName() {
        return "vault-dropwizard";
    }

    @Override
    public void initialize(final Bootstrap<VaultDropwizardConfiguration> bootstrap) {
        // Enable variable substitution with environment variables
        bootstrap.setConfigurationSourceProvider(
                new SubstitutingSourceProvider(
                        bootstrap.getConfigurationSourceProvider(),
                        new EnvironmentVariableSubstitutor(false)
                )
                                                );
    }

    @Override
    public void run(final VaultDropwizardConfiguration configuration,
                    final Environment environment) {

        MysqlConnectionPoolDataSource dataSource = provideDataSource(configuration.getDatabaseConfiguration());
        environment.healthChecks().register("database", new DatabaseHealthCheck(dataSource));
    }

    private MysqlConnectionPoolDataSource provideDataSource(DatabaseConfiguration configuration) {
        MysqlConnectionPoolDataSource dataSource = new MysqlConnectionPoolDataSource();
        dataSource.setUrl(configuration.getJdbcUrl());
        dataSource.setUser(configuration.getUser());
        dataSource.setPassword(configuration.getPassword());
        return dataSource;
    }

}
