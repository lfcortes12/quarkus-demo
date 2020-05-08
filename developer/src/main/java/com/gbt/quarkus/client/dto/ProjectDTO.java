package com.gbt.quarkus.client.dto;

import com.google.common.base.MoreObjects;

public class ProjectDTO {

    private String name;
    private String description;

    public ProjectDTO() {
    }

    public ProjectDTO(final String name, final String description) {
        super();
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("name", name)
                .add("description", description)
                .toString();
    }
}
