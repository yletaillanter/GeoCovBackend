import health.TemplateHealthCheck;
import io.dropwizard.Application;
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

    @Override
    public String getName() {
        return "hello-world";
    }

    @Override
    public void initialize(Bootstrap<GeoCovConfiguration> bootstrap) {
        // nothing to do yet
    }

    @Override
    public void run(GeoCovConfiguration geoCovConfiguration, Environment environment) throws Exception {
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
