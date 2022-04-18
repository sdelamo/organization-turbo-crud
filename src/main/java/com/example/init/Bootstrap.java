package com.example.init;

import com.example.repositories.OrganizationRepository;
import io.micronaut.context.annotation.Requires;
import io.micronaut.context.env.Environment;
import io.micronaut.context.event.ApplicationEventListener;
import io.micronaut.context.event.StartupEvent;
import jakarta.inject.Singleton;

@Requires(env = Environment.DEVELOPMENT)
@Singleton
public class Bootstrap implements ApplicationEventListener<StartupEvent> {
    private final OrganizationRepository organizationRepository;

    public Bootstrap(OrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
    }

    @Override
    public void onApplicationEvent(StartupEvent event) {
        if (organizationRepository.count() == 0) {
            for (int i = 1; i < 32; i++) {
                organizationRepository.save("Org " + i);
            }
        }
    }
}
