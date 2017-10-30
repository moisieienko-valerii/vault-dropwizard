package mvp.vault.demo;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 */
public class DatabaseConfiguration {
    private String jdbcUrl;
    private String user;
    private String password;

    @JsonProperty("jdbcUrl")
    public String getJdbcUrl() {
        return this.jdbcUrl;
    }

    @JsonProperty("jdbcUrl")
    public void setJdbcUrl(String jdbcUrl) {
        this.jdbcUrl = jdbcUrl;
    }

    @JsonProperty("user")
    public String getUser() {
        return this.user;
    }

    @JsonProperty("user")
    public void setUser(String user) {
        this.user = user;
    }

    @JsonProperty("password")
    public String getPassword() {
        return this.password;
    }

    @JsonProperty("password")
    public void setPassword(String password) {
        this.password = password;
    }
}
