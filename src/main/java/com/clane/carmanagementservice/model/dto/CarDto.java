package com.clane.carmanagementservice.model.dto;

import com.clane.carmanagementservice.model.Car;
import com.clane.carmanagementservice.util.Utility;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class CarDto implements Serializable {

    private String name;
    private String engineNumber;
    private Set<CategoryDto> categories;
    private Set<TagDto> tags;
    private Map<String, String> images = new HashMap<>();
    private String description;

    public CarDto(Car car) {
        name = car.getName();
        engineNumber = car.getEngineNumber();
        categories = car.getCategories().parallelStream().map(category -> new CategoryDto(category.getId(), category.getName())).collect(Collectors.toSet());
        tags = car.getTags().parallelStream().map(tag -> new TagDto(tag.getId(), tag.getName())).collect(Collectors.toSet());
        description = car.getDescription();
        setImages(car.getImages());

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEngineNumber() {
        return engineNumber;
    }

    public void setEngineNumber(String engineNumber) {
        this.engineNumber = engineNumber;
    }

    public Set<CategoryDto> getCategories() {
        return categories;
    }

    public void setCategories(Set<CategoryDto> categories) {
        this.categories = categories;
    }

    public Set<TagDto> getTags() {
        return tags;
    }

    public void setTags(Set<TagDto> tags) {
        this.tags = tags;
    }

    public Map<String, String> getImages() {
        return images;
    }

    public void setImages(Map<String, String> images) {
        this.images = images;

        this.images.forEach((k,v) -> this.images.put(k, Utility.convertImageToBase64(v)));
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

//    @JsonIgnore
//    public CarDto mapToCarDto(Car car){
//        Set<CategoryDto> categoryDtos = new HashSet<>();
//        Set<TagDto> tagDtos = new HashSet<>();
//        this.setName(car.getName());
//        //this.setImages(car.getImages());
//        this.setEngineNumber(car.getEngineNumber());
//
//        for (Category category : car.getCategories()){
//
//            categoryDtos.add(new CategoryDto(category.getId(), category.getName()));
//        }
//        this.setCategories(categoryDtos);
//        for (Tag tag : car.getTags()){
//            tagDtos.add(new TagDto(tag.getId(), tag.getName()));
//        }
//        this.setTags(tagDtos);
//
//        ObjectMapper mapper = new ObjectMapper();
//        try {
//
//            // convert JSON string to Map
//            Map<String, String> map = mapper.readValue(car.getImages(), Map.class);
//            for(Map.Entry<String, String> entry : map.entrySet()){
//                String imagePath = entry.getValue();
//                entry.setValue(Utility.convertImageToBase64(imagePath));
//            }
//            // it works
//            //Map<String, String> map = mapper.readValue(json, new TypeReference<Map<String, String>>() {});
//
//            this.setImages(map);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        //this.setCategories();
//        return this;
//    }
}
