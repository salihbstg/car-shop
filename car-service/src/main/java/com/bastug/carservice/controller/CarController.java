package com.bastug.carservice.controller;

import com.bastug.carservice.dtos.CarResponse;
import com.bastug.carservice.dtos.CreateCarRequest;
import com.bastug.carservice.dtos.UpdateCarRequest;
import com.bastug.carservice.enums.FuelType;
import com.bastug.carservice.service.CarService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/cars")
@RequiredArgsConstructor
public class CarController {
    private final CarService carService;


    @PostMapping
    public ResponseEntity<CarResponse> createCar(@Valid @RequestBody CreateCarRequest createCarRequest,@RequestHeader("Authorization") String token) {
        System.out.println("Gel all caars");
        return ResponseEntity.status(HttpStatus.CREATED).body(carService.createCar(createCarRequest,token));
    }
    @GetMapping
    public ResponseEntity<Page<CarResponse>> getAllCars(
            Pageable pageable,
            @RequestParam(required = false) FuelType fuelType,
            @RequestParam(required = false) String brand
    ) {
        if(fuelType != null && brand != null) {
            return ResponseEntity.ok(carService.getCarsByBrandAndFuelType(fuelType,brand,pageable));
        }
        if(fuelType != null) {
            return ResponseEntity.ok(carService.getCarsByFuelType(fuelType, pageable));
        }
        if(brand != null) {
            return ResponseEntity.ok(carService.getCarsByBrand(brand, pageable));
        }
        return ResponseEntity.ok(carService.getAllCars(pageable));
    }
    @GetMapping("{id}")
    public ResponseEntity<CarResponse> getCarById(@PathVariable(name="id") Long id) {
        return ResponseEntity.ok(carService.getCarById(id));
    }
    @GetMapping("/by-customer-id")
    public ResponseEntity<List<CarResponse>> getCarsByCustomerId(@RequestParam(name = "customerId") Long id){
        return ResponseEntity.ok(carService.getCarsByCustomerId(id));
    }
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteCar(@PathVariable(name = "id") Long id) {
        carService.deleteCar(id);
        return ResponseEntity.noContent().build();
    }
    @PatchMapping("/{id}")
    public ResponseEntity<CarResponse> updateCar(
            @PathVariable Long id,
            @Valid @RequestBody UpdateCarRequest request
    ) {
        return ResponseEntity.ok(carService.updateCar(id, request));
    }
}
