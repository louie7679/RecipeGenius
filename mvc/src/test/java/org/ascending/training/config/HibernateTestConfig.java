package org.ascending.training.config;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import static org.mockito.Mockito.mock;

@Configuration
@Profile("unit")
public class HibernateTestConfig {
    @Bean
    public SessionFactory getHibernateSessionFactory() {
        return mock(SessionFactory.class);
    }
}
