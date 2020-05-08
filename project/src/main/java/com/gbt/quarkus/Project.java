package com.gbt.quarkus;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Entity;

@Entity
public class Project extends PanacheEntity {
    public String name;
    public String description;
}
