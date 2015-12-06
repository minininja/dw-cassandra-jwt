package org.dorkmaster.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import org.stuartgunter.dropwizard.cassandra.CassandraFactory;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Author: mjackson
 * Date: 11/8/15 8:20 AM
 */
public class AuthConfiguration extends Configuration {
    @Valid
    @NotNull
    private CassandraFactory cassandra = new CassandraFactory();

    @NotNull
    private String jwtKey;

    @JsonProperty("cassandra")
    public CassandraFactory getCassandraFactory() {
        return cassandra;
    }

    @JsonProperty("jwtKey")
    public String getJwtKey() {
        return jwtKey;
    }

    @JsonProperty("cassandra")
    public void setCassandraFactory(CassandraFactory cassandra) {
        this.cassandra = cassandra;
    }

    @JsonProperty("swagger")
    public SwaggerBundleConfiguration swaggerBundleConfiguration;

    @JsonProperty("jwtKey")
    public AuthConfiguration setJwtKey(String jwtKey) {
        this.jwtKey = jwtKey;
        return this;
    }
}
