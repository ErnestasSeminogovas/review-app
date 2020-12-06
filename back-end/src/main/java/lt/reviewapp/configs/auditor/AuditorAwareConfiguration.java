package lt.reviewapp.configs.auditor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
public class AuditorAwareConfiguration {
    @Bean
    public AuditorAware<Integer> auditorAware() {
        return new AuditorAwareImpl();
    }
}
