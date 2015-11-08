package org.dorkmaster.auth.dao;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import org.dorkmaster.auth.model.User;

import javax.inject.Inject;

/**
 * Author: mjackson
 * Date: 11/8/15 8:22 AM
 */
public class UserDao {
    private Cluster cassandra;

    @Inject
    public UserDao(Cluster cassandra) {
        this.cassandra = cassandra;
    }

    public User findByUsernameAndPassword(String username, String password) {
        Session session = null;
        PreparedStatement ps;
        try {
            session = cassandra.connect();

            Mapper<User> mapper = new MappingManager(session).mapper(User.class);
            User user = mapper.get(username);
            if (user != null && user.getPassword().equals(password)) {
                return user;
            }

        } finally {
            session.close();
        }
        return null;
    }
}
