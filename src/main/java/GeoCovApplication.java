import core.Client;
import db.AdresseDAO;
import db.ClientDAO;
import db.GroupeDAO;
import health.TemplateHealthCheck;
import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.db.PooledDataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import resources.HelloWorldResource;

/**
 * Created by yoannlt on 06/01/2016.
 */
public class GeoCovApplication extends Application<GeoCovConfiguration> {

    public static void main(String[] args) throws Exception {
        new GeoCovApplication().run(args);
    }

    /**
     * TODO : Mieux gérer les différents DAO
     */
    private final HibernateBundle<GeoCovConfiguration> hibernateBundle =
            new HibernateBundle<GeoCovConfiguration>(Client.class) {
                @Override
                public DataSourceFactory getDataSourceFactory(GeoCovConfiguration configuration) {
                    return configuration.getDataSourceFactory();
                }
            };

    @Override
    public String getName() {
        return "hello-world";
    }

    @Override
    public void initialize(Bootstrap<GeoCovConfiguration> bootstrap) {
        // nothing to do yet
        bootstrap.addBundle(hibernateBundle);
    }

    @Override
    public void run(GeoCovConfiguration geoCovConfiguration, Environment environment) throws Exception {
        final AdresseDAO adao = new AdresseDAO(hibernateBundle.getSessionFactory());
        final ClientDAO cdao = new ClientDAO(hibernateBundle.getSessionFactory());
        final GroupeDAO gdao = new GroupeDAO(hibernateBundle.getSessionFactory());

        final HelloWorldResource resource = new HelloWorldResource(
                geoCovConfiguration.getTemplate(),
                geoCovConfiguration.getDefaultName()
        );
        environment.jersey().register(resource);

        final TemplateHealthCheck healthCheck =
                new TemplateHealthCheck(geoCovConfiguration.getTemplate());
        environment.healthChecks().register("template", healthCheck);
        environment.jersey().register(resource);
    }
}
