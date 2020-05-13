package com.gbt.quarkus;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.runtime.annotations.RegisterForReflection;

import javax.persistence.Entity;

@RegisterForReflection
@Entity
public class Seniority extends PanacheEntity {

    public String name;

    public Seniority() {
    }

    public Seniority(String name) {
        this.name = name;
    }
}
