package org.dorkmaster.auth.api;

import com.codahale.metrics.annotation.ExceptionMetered;
import com.codahale.metrics.annotation.Timed;
import com.google.common.base.Joiner;
import org.dorkmaster.auth.dao.UserDao;
import org.dorkmaster.auth.model.User;
import org.jose4j.jwe.ContentEncryptionAlgorithmIdentifiers;
import org.jose4j.jwe.JsonWebEncryption;
import org.jose4j.jwe.KeyManagementAlgorithmIdentifiers;
import org.jose4j.keys.AesKey;
import org.jose4j.lang.JoseException;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import java.security.Key;

/**
 * Author: mjackson
 * Date: 11/8/15 8:22 AM
 */
@Path("/auth")
public class AuthResource {
    private UserDao userDao;
    private String key;

    public static final String DELIMITER = "::";

    @Inject
    public AuthResource(String key, UserDao userDao) {
        this.key = key;
        this.userDao = userDao;
    }

    @GET
    @Path("/")
    @Timed
    @ExceptionMetered
    // TODO switch these from query parameter to body and make it a post
    public String auth(@QueryParam("username") String username, @QueryParam("password") String password) {
        try {
            User user = userDao.findByUsernameAndPassword(username, password);
            if (user != null) {
                Key key = new AesKey(this.key.getBytes());
                JsonWebEncryption jwe = new JsonWebEncryption();
                if (user.getRoles() != null && !user.getRoles().isEmpty()) {
                    jwe.setPayload(user.getUsername() + DELIMITER + Joiner.on(",").join(user.getRoles()));
                } else {
                    jwe.setPayload(user.getUsername());
                }

                jwe.setAlgorithmHeaderValue(KeyManagementAlgorithmIdentifiers.A128KW);
                jwe.setEncryptionMethodHeaderParameter(ContentEncryptionAlgorithmIdentifiers.AES_128_CBC_HMAC_SHA_256);
                jwe.setKey(key);
                String tmp = jwe.getCompactSerialization();

                jwe = new JsonWebEncryption();
                jwe.setKey(key);
                jwe.setCompactSerialization(tmp);
                System.out.println("Payload: " + jwe.getPayload());

                return tmp;
            }
        } catch (JoseException e) {
            // we're just going to fail the request anyway
            e.printStackTrace();
        }
        throw new WebApplicationException(401);
    }

}
