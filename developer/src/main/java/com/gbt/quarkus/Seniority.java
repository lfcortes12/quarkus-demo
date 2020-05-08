package com.gbt.quarkus;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.runtime.annotations.RegisterForReflection;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Set;

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
