package com.clane.carmanagementservice.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Set;

public class CarRequest implements Serializable {



    private String name;
    @NotNull
    private String description;
    @NotNull
    @JsonProperty("category_ids")
    private Set<Long> categoryIds;
    @NotNull
    @JsonProperty("tag_ids")
    private Set<Long> tagIds;

    @NotNull
    private String[] images;

    @NotNull
    private String color;

    public CarRequest() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Long> getCategoryIds() {
        return categoryIds;
    }

    public void setCategoryIds(Set<Long> categoryIds) {
        this.categoryIds = categoryIds;
    }

    public Set<Long> getTagIds() {
        return tagIds;
    }

    public void setTagIds(Set<Long> tagIds) {
        this.tagIds = tagIds;
    }

    public String[] getImages() {
        return images;
    }

    public void setImages(String[] images) {
        this.images = images;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
