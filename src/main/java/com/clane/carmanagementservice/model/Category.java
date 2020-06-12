package com.clane.carmanagementservice.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "categories")
public class Category extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -9017650847571487336L;

    @NotEmpty(message = "Category name must not be empty")
    private String name;


    @ManyToMany(mappedBy = "categories", fetch = FetchType.LAZY)
    private Set<Car> car;



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
