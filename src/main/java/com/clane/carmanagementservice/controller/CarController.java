package com.clane.carmanagementservice.controller;


import com.clane.carmanagementservice.model.dto.CarDto;
import com.clane.carmanagementservice.model.dto.CarRequest;
import com.clane.carmanagementservice.model.dto.CreateImageRequest;
import com.clane.carmanagementservice.service.CarService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController()
@RequestMapping("/cars")
public class CarController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CarController.class);

    @Autowired
    private CarService carService;


    @GetMapping()
    public List<CarDto> getCars(){
       // LOGGER.info("Received Request to convert from {} {} to {}. ", quantity, from, to);
        return carService.getCars();
    }

    @GetMapping("/{engineNumber}")
    public CarDto getCarById(@PathVariable String engineNumber){
        return carService.getCar(engineNumber);
    }
    @PostMapping()
    public ResponseEntity<CarDto> saveCar(@Valid @RequestBody CarRequest CarRequest, HttpServletRequest request){
        return carService.saveCar(CarRequest, request);
    }

    @PutMapping("/{engineNumber}")
    public ResponseEntity<CarDto> updateCar(@RequestBody CarRequest carRequest, @PathVariable String engineNumber){
        return carService.updateCar(engineNumber, carRequest);
    }

    @GetMapping("/search/{query}")
    public ResponseEntity<List<CarDto>> Search(@PathVariable String query){
        return carService.search(query);
    }


    @PostMapping("/{id}/images")
    public ResponseEntity<CarDto> addImage(@PathVariable String id, @RequestBody CreateImageRequest imageRequest){
        return carService.addImage(id, imageRequest);
    }

    @DeleteMapping("/{carId}/images/{imageId}")
    public ResponseEntity<CarDto> addImage(@PathVariable String carId, @PathVariable String imageId){
        return carService.deleteImage(carId, imageId);
    }





}
