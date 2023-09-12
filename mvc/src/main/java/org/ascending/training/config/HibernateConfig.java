package org.ascending.training.config;

import org.ascending.training.util.HibernateUtil;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("dev")
public class HibernateConfig {
    @Bean
    public SessionFactory getHibernateSessionFactory() {
        return HibernateUtil.getSessionFactory();
    }
}
