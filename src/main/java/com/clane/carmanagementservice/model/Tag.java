package com.clane.carmanagementservice.model;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "tags")
public class Tag extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -9017650847571487336L;

    @NotEmpty(message = "tag name must not be empty")
    private String name;


    @ManyToMany(mappedBy = "tags")
    private Set<Car> car;

    public Tag() {
    }

    public Tag(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Car> getCar() {
        return car;
    }

    public void setCar(Set<Car> car) {
        this.car = car;
    }
}
