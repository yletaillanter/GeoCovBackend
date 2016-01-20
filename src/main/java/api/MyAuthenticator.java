package api;

import core.Client;
import com.google.common.base.Optional;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;
import io.dropwizard.auth.AuthenticationException;

/**
 * Created by yoannlt on 20/01/2016.
 */
public class MyAuthenticator implements Authenticator<BasicCredentials, Client> {
    @Override
    public Optional<Client> authenticate(BasicCredentials credentials) throws AuthenticationException {
        if ("password".equals(credentials.getPassword())) {
            return Optional.of(new Client());
        }
        return Optional.absent();
    }
    //new Client(credentials.getUsername())
}
