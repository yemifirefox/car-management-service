package com.clane.carmanagementservice.service;

import com.clane.carmanagementservice.exception.ResourceNotFoundException;
import com.clane.carmanagementservice.model.Car;
import com.clane.carmanagementservice.model.Category;
import com.clane.carmanagementservice.model.Tag;
import com.clane.carmanagementservice.model.dto.CarDto;
import com.clane.carmanagementservice.model.dto.CarRequest;
import com.clane.carmanagementservice.model.dto.CreateImageRequest;
import com.clane.carmanagementservice.repository.CarRepository;
import com.clane.carmanagementservice.repository.CategoryRepository;
import com.clane.carmanagementservice.repository.TagRepository;
import com.clane.carmanagementservice.util.Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Service
public class CarService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CarService.class);

    private final CarRepository carRepository;
    private final CategoryRepository categoryRepository;
    private final TagRepository tagRepository;
    private final Utility utility;

    public CarService(CarRepository carRepository, CategoryRepository categoryRepository,
                      TagRepository tagRepository, Utility utility) {
        this.carRepository = carRepository;
        this.categoryRepository = categoryRepository;
        this.tagRepository = tagRepository;
        this.utility = utility;
    }


    public List<CarDto> getCars(){
        return carRepository.getAllCars();
    }

    public CarDto getCar(String engineNumber){
        Optional<CarDto> carDto = carRepository.getCar(engineNumber);
        if (!carDto.isPresent()){
            LOGGER.info("Invalid engine Number : " + engineNumber);
            throw new ResourceNotFoundException("Invalid engine Number : " + engineNumber);
        }
        return carDto.get();

    }


    public ResponseEntity<CarDto> saveCar(CarRequest carRequest){
        Set<Tag> tags;
        Set<Category> categories;

        Car carResponse = carRepository.findByNameLike(carRequest.getName());
        if (carResponse != null ){
            LOGGER.info("CAR already exist");

            throw new ResourceNotFoundException("CAR already exist");
        }
        /*if (carRequest.getName() != null && carRequest.getCategoryIds() == null &&
                carRequest.getTagIds() == null && carRequest.getColor() == null &&
                carRequest.getDescription() == null && carRequest.getImages() == null
        ) {
            Car car =  carRepository.findByNameLike(carRequest.getName());
            car.setName(carRequest.getName());
            Car resp = carRepository.save(car);
        }*/

        tags = tagRepository.findByIdIn(carRequest.getTagIds());
        if (tags.size() != carRequest.getTagIds().size()) {
            LOGGER.info("selected tag not valid");
            throw new ResourceNotFoundException("selected tag not valid.");
        }
        categories = categoryRepository.findByIdIn(carRequest.getCategoryIds());


        Car car = new Car();
        car.setName(carRequest.getName());
        car.setColor(carRequest.getColor());
        car.setDescription(carRequest.getDescription());
        car.setTags(tags);
        car.setCategories(categories);
        Map<String, String> imgPath =  utility.createImageAndAddPathToMap(carRequest.getImages());
        car.setImages(imgPath);
        CarDto carDto = new CarDto(carRepository.save(car));
        return new ResponseEntity<>(carDto, HttpStatus.CREATED);
    }


    public ResponseEntity<CarDto> updateCar(String engineNumber, CarRequest carRequest){
        Optional<Car> carResponse = carRepository.findByEngineNumber(engineNumber);
        if(!carResponse.isPresent()){
            throw new ResourceNotFoundException("Invalid engine Number : " + engineNumber);
        }
        Car car = carResponse.get();
            if (carRequest.getName() != null)
                car.setName(carRequest.getName());

            if(carRequest.getDescription() != null){
                car.setDescription(carRequest.getDescription());
            }
            if(carRequest.getColor() != null){
                car.setColor(carRequest.getColor());
            }

            if (carRequest.getCategoryIds() != null && !carRequest.getCategoryIds().isEmpty()) {

                Set<Category> categories = categoryRepository.findByIdIn(carRequest.getCategoryIds());

                car.setCategories(categories);
            }
            if (carRequest.getTagIds() != null && !carRequest.getTagIds().isEmpty()) {

                Set<Tag> tags = tagRepository.findByIdIn(carRequest.getTagIds());

                car.setTags(tags);
            }


            return new ResponseEntity<>(new CarDto(carRepository.save(car)), HttpStatus.OK);

    }

    public ResponseEntity<List<CarDto>> search(String query) {

        List<CarDto> carDtos =  carRepository.search(query.toLowerCase());

        return new ResponseEntity<>(carDtos, HttpStatus.OK);
    }

    public ResponseEntity<CarDto> addImage(String id, CreateImageRequest imageRequest) {
        Optional<Car> car  = carRepository.findByEngineNumber(id);
        if (!car.isPresent()){
            throw new ResourceNotFoundException("Car with Engine Number : " + id +" does not exist");
        }

        if (car.get().getImages().size() < 1){
            car.get().setImages(utility.createImageAndAddPathToMap());
        }else {
            Map<String, String> newImage = utility.createImageAndAddPathToMap(imageRequest.getImages());
            newImage.forEach((k, v) -> car.get().getImages().put(k, v));
        }
       return new ResponseEntity<>(new CarDto(carRepository.save(car.get())), HttpStatus.OK);
    }

    public ResponseEntity<CarDto> deleteImage(String engineNumber, String imageId) {
        Optional<Car> car  = carRepository.findByEngineNumber(engineNumber);
        if (!car.isPresent()){
            throw new ResourceNotFoundException("Car with Engine Number : " + engineNumber +" does not exist");
        }
        String imageTobeRemoved = car.get().getImages().get(imageId);
        if (imageTobeRemoved == null){
            throw new ResourceNotFoundException("Image Id: " +imageId +"does not exist");
        }

        if (!utility.deleteImage(imageTobeRemoved)){
            throw new ResourceNotFoundException("Image could not be deleted from path");
        }
        car.get().getImages().remove(imageTobeRemoved);
        return new ResponseEntity<>(new CarDto(carRepository.save(car.get())), HttpStatus.OK);

    }

}
