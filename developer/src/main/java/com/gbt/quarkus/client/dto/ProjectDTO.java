package com.gbt.quarkus.client.dto;

public class ProjectDTO {
	private String name;
	private String description;

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
}
