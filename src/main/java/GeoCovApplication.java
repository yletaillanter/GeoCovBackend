import core.Adresse;
import core.Client;
import core.Groupe;
import db.AdresseDAO;
import db.ClientDAO;
import db.GroupeDAO;
import health.TemplateHealthCheck;
import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.;
import org.skife.jdbi.v2.DBI;
import resources.AdresseResource;
import resources.ClientResource;
import resources.GroupeResource;

/**
 * Created by yoannlt on 06/01/2016.
 */
public class GeoCovApplication extends Application<GeoCovConfiguration> {

    /**
     *  Init Hibernate Bundle
     */
    private final HibernateBundle<GeoCovConfiguration> hibernateBundle =
            new HibernateBundle<GeoCovConfiguration>(Client.class, Groupe.class, Adresse.class) {
                @Override
                public DataSourceFactory getDataSourceFactory(GeoCovConfiguration configuration) {
                    return configuration.getDataSourceFactory();
                }
            };

    /**
     *  Init Migration Bundle
     */
    private final MigrationsBundle<GeoCovConfiguration> migrationsBundle =
            new MigrationsBundle<GeoCovConfiguration>() {
                @Override
                public DataSourceFactory getDataSourceFactory(GeoCovConfiguration conf) {
                    return conf.getDataSourceFactory();
                }
            };

    public static void main(String[] args) throws Exception {
        new GeoCovApplication().run(args);
    }

    @Override
    public String getName() {
        return "geo-cov";
    }

    @Override
    public void initialize(Bootstrap<GeoCovConfiguration> bootstrap) {
        bootstrap.addBundle(hibernateBundle);
        bootstrap.addBundle(migrationsBundle);
        //bootstrap.addBundle(new MultiPartBundle());
    }

    @Override
    public void run(GeoCovConfiguration geoCovConfiguration, Environment environment) throws Exception {
        // Init DB
        final DBIFactory factory = new DBIFactory();
        final DBI jdbi = factory.build(environment, geoCovConfiguration.getDataSourceFactory(), "mysql");

        // Add client Ressources
        final ClientDAO cdao = new ClientDAO(hibernateBundle.getSessionFactory());
        environment.jersey().register(new ClientResource(cdao));

        // Add groupe Ressources
        final AdresseDAO adao = new AdresseDAO(hibernateBundle.getSessionFactory());
        environment.jersey().register(new AdresseResource(adao));

        // Add groupe Ressources
        final GroupeDAO gdao = new GroupeDAO(hibernateBundle.getSessionFactory());
        environment.jersey().register(new GroupeResource(gdao));

        final TemplateHealthCheck healthCheck =
                new TemplateHealthCheck(geoCovConfiguration.getTemplate());
        environment.healthChecks().register("template", healthCheck);
    }
}
