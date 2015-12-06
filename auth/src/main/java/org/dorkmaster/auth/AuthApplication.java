package org.dorkmaster.auth;

import com.datastax.driver.core.Cluster;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import org.dorkmaster.auth.api.UserResource;
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
    public void initialize(Bootstrap<AuthConfiguration> bootstrap) {
//        super.initialize(bootstrap);

        bootstrap.addBundle(new SwaggerBundle<AuthConfiguration>() {
            @Override
            protected SwaggerBundleConfiguration getSwaggerBundleConfiguration(AuthConfiguration configuration) {
                return configuration.swaggerBundleConfiguration;
            }
        });
    }

    @Override
    public void run(AuthConfiguration configuration, Environment environment) throws Exception {
        Cluster cassandra = configuration.getCassandraFactory().build(environment);

        environment.jersey().register(new UserResource(configuration.getJwtKey(), new UserDao(cassandra)));
//        environment.jersey().register(UserResource.class);

        // TODO cassandra.close()?
    }
}
