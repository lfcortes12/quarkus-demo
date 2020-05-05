package com.gbt.quarkus;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Set;

@Entity
public class Seniority extends PanacheEntity {

    @Column
    public String name;

    @JsonbTransient
    @OneToMany
    public Set<Developer> developers;

    public Seniority() {
    }

    public Seniority(String name) {
        this.name = name;
    }
}
