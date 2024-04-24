package nbcc.termproject.config;


import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class EnvironmentConfig implements  EmailConfig {

    private final Environment environment;

    public EnvironmentConfig(Environment environment) {
        this.environment = environment;
    }


    @Override
    public String getDefaultFromEmailAddress() {
        return environment.getProperty("Default.From.Email");
    }
}