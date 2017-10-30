package mvp.vault.demo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class VaultDropwizardConfiguration extends Configuration {
    @Valid
    @NotNull
    private DatabaseConfiguration databaseConfiguration = new DatabaseConfiguration();

    @JsonProperty("database")
    public DatabaseConfiguration getDatabaseConfiguration() {
        return this.databaseConfiguration;
    }

    @JsonProperty("database")
    public void setDatabaseConfiguration(DatabaseConfiguration databaseConfiguration) {
        this.databaseConfiguration = databaseConfiguration;
    }

}
