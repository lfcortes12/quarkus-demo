package com.gbt.quarkus.service;


import com.gbt.quarkus.Developer;
import com.gbt.quarkus.Seniority;
import io.quarkus.runtime.StartupEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.transaction.Transactional;
import java.util.stream.Stream;

@ApplicationScoped
public class DeveloperService {

    Logger log = LoggerFactory.getLogger(DeveloperService.class);

    @Transactional
    public void startup(@Observes StartupEvent startupEvent) {
        log.debug("Creating initial data");

        Seniority junior = new Seniority("Junior");
        Seniority senior = new Seniority( "Senior");

        Seniority.persist(Stream.of(junior, senior));

        Developer dev1 = new Developer("Luis", "Spring", junior);
        Developer dev2 = new Developer("Breymer", "Quarkus", senior);
        Developer dev3 = new Developer(  "George", "Quarkus", senior);
        Developer dev4 = new Developer("Esteban", "Spring", junior);
        Developer.persist(Stream.of(dev1, dev2, dev3, dev4));
    }

}
