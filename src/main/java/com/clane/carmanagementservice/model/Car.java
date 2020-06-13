package com.clane.carmanagementservice.model;

import com.clane.carmanagementservice.util.MySequenceIdGenerator;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


@Entity
@Table(name = "cars")
@TypeDefs({@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)})
public class Car implements Serializable{

    private static final long serialVersionUID = -9017650847571487336L;


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "car_seq")
    @GenericGenerator(
            name = "car_seq",
            strategy = "com.clane.carmanagementservice.util.MySequenceIdGenerator",
            parameters = {
                    @Parameter(name = MySequenceIdGenerator.INCREMENT_PARAM, value = "50"),
                    ///@Parameter(name = StringPrefixedSequenceIdGenerator.VALUE_PREFIX_PARAMETER, value = "B_"),
                    @Parameter(name = MySequenceIdGenerator.NUMBER_FORMAT_PARAMETER, value = "%05d") })
    @Column(name = "engine_number")
    private String engineNumber;


    @NotEmpty(message = "name name must not be empty")
    private String name;

    private String description;


    @ManyToMany(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    @JoinTable(
            name = "car_category",
            joinColumns = @JoinColumn(name = "car_engine"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<Category> categories = new HashSet<>();

    @ManyToMany(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    @JoinTable(
            name = "car_tag",
            joinColumns = @JoinColumn(name = "car_engine"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<Tag> tags = new HashSet<>();


    @Column(name = "images", columnDefinition = "jsonb")
    @Type(type = "jsonb")
    private Map<String, String> images;

    private String color;

    public Car() {
    }


    public Car(String name, String description, Map<String, String> images, String color) {
        this.name = name;
        this.description = description;
        this.images = images;
        this.color = color;
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

    public String getEngineNumber() {
        return engineNumber;
    }

    public void setEngineNumber(String engineNumber) {
        this.engineNumber = engineNumber;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public Map<String, String> getImages() {
        return images;
    }

    public void setImages(Map<String, String> images) {
        this.images = images;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }


}
