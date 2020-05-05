package com.gbt.quarkus;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Entity
public class Developer extends PanacheEntity implements Serializable {

    //Entities already have an ID field
    public Developer() {
    }

    public Developer(String name, String platform, Seniority seniority) {
        this.name = name;
        this.platform = platform;
        this.seniority = seniority;
    }

    // Get and setters are generated and also field visibility is changed properly
    @Column
    public String name;

    @Column
    public String platform;

    @ManyToOne(fetch = FetchType.LAZY)
    public Seniority seniority;

    //with active record pattern the logic to access to the data should be by using static methods
    static List<Developer> getByPlatform(String platform) {
        return find("platform", platform).list();
    }

    static List<Developer> getByPlatformNotCase(String platform) {
        Map<String, Object> params = Map.of("platform", platform.toLowerCase());
        return find("lower(platform) = :platform", params).list();
    }

    static List<Developer> searchByPlatform(String platform) {
        Map<String, Object> params = Map.of("platform", "%" + platform + "%");
        return find("lower(platform) like :platform", params).list();
    }

}
