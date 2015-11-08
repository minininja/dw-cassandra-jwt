package org.dorkmaster.auth;

import com.datastax.driver.core.Cluster;
import io.dropwizard.Application;
import io.dropwizard.setup.Environment;
import org.dorkmaster.auth.api.AuthResource;
import org.dorkmaster.auth.dao.UserDao;

/**
 * Author: mjackson
 * Date: 11/8/15 8:21 AM
 */
public class AuthApplication extends Application<AuthConfiguration> {

    public static void main(String[] args) throws Exception {
        new AuthApplication().run(args);
    }

    @Override
    public void run(AuthConfiguration configuration, Environment environment) throws Exception {
        Cluster cassandra = configuration.getCassandraFactory().build(environment);

        environment.jersey().register(new AuthResource(configuration.getJwtKey(), new UserDao(cassandra)));

        // TODO cassandra.close()?
    }
}
