package com.clane.carmanagementservice.model.dto;

import javax.validation.constraints.NotNull;

public class CreateImageRequest {


    @NotNull
    private String[] images;

    public String[] getImages() {
        return images;
    }

    public void setImages(String[] images) {
        this.images = images;
    }
}



