package com.gbt.quarkus.repository;

import com.gbt.quarkus.Developer;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class DeveloperRepository implements PanacheRepository<Developer> {

    public List<Developer> findByName(String name) {
        return find("select d from Developer d where lower(d.name) like :name",
                Map.of("name", getNameParam(name))).list();
    }

    private static String getNameParam(String name) {
        return String.format("%%%s%%", name.toLowerCase());
    }
}
