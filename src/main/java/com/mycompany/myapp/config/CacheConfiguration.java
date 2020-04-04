package com.mycompany.myapp.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import org.hibernate.cache.jcache.ConfigSettings;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, com.mycompany.myapp.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, com.mycompany.myapp.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, com.mycompany.myapp.domain.User.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Authority.class.getName());
            createCache(cm, com.mycompany.myapp.domain.User.class.getName() + ".authorities");
            createCache(cm, com.mycompany.myapp.domain.Candidat.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Candidat.class.getName() + ".tanonyms");
            createCache(cm, com.mycompany.myapp.domain.Candidat.class.getName() + ".notes");
            createCache(cm, com.mycompany.myapp.domain.Centre.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Centre.class.getName() + ".salles");
            createCache(cm, com.mycompany.myapp.domain.Correcteur.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Correcteur.class.getName() + ".matieres");
            createCache(cm, com.mycompany.myapp.domain.Examen.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Examen.class.getName() + ".juries");
            createCache(cm, com.mycompany.myapp.domain.Jury.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Jury.class.getName() + ".examen");
            createCache(cm, com.mycompany.myapp.domain.Matiere.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Matiere.class.getName() + ".correcteurs");
            createCache(cm, com.mycompany.myapp.domain.Note.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Plage.class.getName());
            createCache(cm, com.mycompany.myapp.domain.PVSurveillance.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Salle.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Salle.class.getName() + ".tables");
            createCache(cm, com.mycompany.myapp.domain.Salle.class.getName() + ".surveillants");
            createCache(cm, com.mycompany.myapp.domain.Surveillant.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Surveillant.class.getName() + ".salles");
            createCache(cm, com.mycompany.myapp.domain.Tables.class.getName());
            createCache(cm, com.mycompany.myapp.domain.TAnonym.class.getName());
            createCache(cm, com.mycompany.myapp.domain.TAnonym.class.getName() + ".tours");
            createCache(cm, com.mycompany.myapp.domain.Tour.class.getName());
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache == null) {
            cm.createCache(cacheName, jcacheConfiguration);
        }
    }

}
