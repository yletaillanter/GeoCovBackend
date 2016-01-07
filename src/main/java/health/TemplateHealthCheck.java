package health;

import com.codahale.metrics.health.HealthCheck;

/**
 * Created by yoannlt on 07/01/2016.
 */
public class TemplateHealthCheck extends HealthCheck {
    private final String template;

    public TemplateHealthCheck(String template) {
        this.template = template;
    }

    @Override
    protected HealthCheck.Result check() throws Exception {
        final String saying = String.format(template, "TEST");
        if (!saying.contains("TEST")) {
            return HealthCheck.Result.unhealthy("template doesn't include a name");
        }
        return HealthCheck.Result.healthy();
    }
}
