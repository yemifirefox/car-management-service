package com.clane.carmanagementservice.controller;


import com.clane.carmanagementservice.model.dto.CarDto;
import com.clane.carmanagementservice.model.dto.CarRequest;
import com.clane.carmanagementservice.model.dto.CreateImageRequest;
import com.clane.carmanagementservice.service.CarService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController()
@RequestMapping("/cars")
public class CarController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CarController.class);


    private CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping()
    public List<CarDto> getCars(){
        return carService.getCars();
    }

    @GetMapping("/{engineNumber}")
    public CarDto getCarById(@PathVariable String engineNumber){
        return carService.getCar(engineNumber);
    }
    @PostMapping()
    public ResponseEntity<CarDto> saveCar(@Valid @RequestBody CarRequest CarRequest){
        LOGGER.info("Received Request to save car");
        return carService.saveCar(CarRequest);
    }

    @PutMapping("/{engineNumber}")
    public ResponseEntity<CarDto> updateCar(@RequestBody CarRequest carRequest, @PathVariable String engineNumber){
        LOGGER.info("Received Request to update car");
        return carService.updateCar(engineNumber, carRequest);
    }

    @GetMapping("/search/{query}")
    public ResponseEntity<List<CarDto>> Search(@PathVariable String query){
        LOGGER.info("Received Request to get car by Engine");
        return carService.search(query);
    }


    @PostMapping("/{id}/images")
    public ResponseEntity<CarDto> addImage(@PathVariable String id, @RequestBody CreateImageRequest imageRequest){
        LOGGER.info("Received Request to Add Image");
        return carService.addImage(id, imageRequest);
    }

    @DeleteMapping("/{carId}/images/{imageId}")
    public ResponseEntity<CarDto> addImage(@PathVariable String carId, @PathVariable String imageId){
        LOGGER.info("Received Request to Delete Image");
        return carService.deleteImage(carId, imageId);
    }





}
