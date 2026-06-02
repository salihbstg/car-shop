package com.bastug.carservice.service;

import com.bastug.carservice.dtos.CarResponse;
import com.bastug.carservice.dtos.CreateCarRequest;
import com.bastug.carservice.dtos.CustomerResponse;
import com.bastug.carservice.dtos.UpdateCarRequest;
import com.bastug.carservice.entity.Car;
import com.bastug.carservice.entity.ImageUrl;
import com.bastug.carservice.enums.FuelType;
import com.bastug.carservice.exception.CarNotFoundException;
import com.bastug.carservice.exception.DuplicatePlateException;
import com.bastug.carservice.feign.CustomerClient;
import com.bastug.carservice.mapper.CarMapper;
import com.bastug.carservice.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final CarMapper carMapper;
    private final CustomerClient customerClient;

    @Override
    public CarResponse createCar(CreateCarRequest createCarRequest,String token) {
        if(carRepository.existsByPlate(createCarRequest.getPlate())){
            throw new DuplicatePlateException(createCarRequest.getPlate());
        }
        CustomerResponse customer=customerClient.getCustomerByToken(token);
        Car car=carMapper.toCar(createCarRequest);
        List<ImageUrl> imageUrls=new ArrayList<>();
        for(String url:createCarRequest.getImageUrls()){
            ImageUrl imageUrl=new ImageUrl();
            imageUrl.setImageUrl(url);
            imageUrl.setCar(car);
            imageUrls.add(imageUrl);
        }
        car.setImageUrls(imageUrls);
        car.setCustomerId(customer.getId());
        carRepository.save(car);
        CarResponse carResponse= carMapper.toCarResponse(car);
        carResponse.setImageUrls(createCarRequest.getImageUrls());
        carResponse.setCustomerResponse(customer);
        return carResponse;
    }

    @Override
    public Page<CarResponse> getAllCars(Pageable pageable) {

        return carRepository.findAll(pageable)
                .map(car->{
                    List<String> urls=new ArrayList<>();
                    CarResponse carResponse=carMapper.toCarResponse(car);
                    carResponse.setCustomerResponse(customerClient.getCustomer(car.getCustomerId()));
                    for(ImageUrl url:car.getImageUrls()){
                        urls.add(url.getImageUrl());
                    }
                    carResponse.setImageUrls(urls);
                    return carResponse;
                });
    }

    @Override
    public void deleteCar(Long id) {
        Car car = carRepository.findById(id).orElseThrow(()-> new CarNotFoundException(id));
        carRepository.delete(car);
    }

    @Override
    public CarResponse updateCar(Long id, UpdateCarRequest request) {
        Optional<Car> optionalCar = carRepository.findById(id);
        if (optionalCar.isPresent()) {
            Car car=optionalCar.get();
            car.setCustomerId(request.getCustomerId());
            carMapper.updateCar(request,car);
            carRepository.save(car);
            return carMapper.toCarResponse(car);
        }
        return null;
    }

    @Override
    public CarResponse getCarById(Long id) {
        Optional<Car> optionalCar = carRepository.findById(id);

        return optionalCar.map((car)->{
            List<String> urls=new ArrayList<>();
            CarResponse carResponse=carMapper.toCarResponse(car);
            for(ImageUrl url:car.getImageUrls()){
                urls.add(url.getImageUrl());
            }
            carResponse.setImageUrls(urls);
            carResponse.setCustomerResponse(customerClient.getCustomer(car.getCustomerId()));
            return carResponse;
        }).orElseThrow(()-> new CarNotFoundException(id));
    }

    @Override
    public Page<CarResponse> getCarsByBrand(String brand, Pageable pageable) {
        return carRepository.findByBrandContainingIgnoreCase(brand,pageable).map(car->{
            List<String> urls=new ArrayList<>();
            CarResponse carResponse=carMapper.toCarResponse(car);
            for(ImageUrl url:car.getImageUrls()){
                urls.add(url.getImageUrl());
            }
            carResponse.setImageUrls(urls);
            carResponse.setCustomerResponse(customerClient.getCustomer(car.getCustomerId()));
            return carResponse;
        });
    }

    @Override
    public Page<CarResponse> getCarsByFuelType(FuelType fuelType, Pageable pageable) {
        return carRepository.findByFuelType(fuelType,pageable).map(car->{
            List<String> urls=new ArrayList<>();
            CarResponse carResponse=carMapper.toCarResponse(car);
            for(ImageUrl url:car.getImageUrls()){
                urls.add(url.getImageUrl());
            }
            carResponse.setImageUrls(urls);
            carResponse.setCustomerResponse(customerClient.getCustomer(car.getCustomerId()));
            return carResponse;
        });
    }

    @Override
    public Page<CarResponse> getCarsByBrandAndFuelType(FuelType fuelType, String brand, Pageable pageable) {
        return carRepository.findByFuelTypeAndBrandContainingIgnoreCase(fuelType,brand,pageable).map(car->{
            List<String> urls=new ArrayList<>();
            for(ImageUrl url:car.getImageUrls()){
                urls.add(url.getImageUrl());
            }
            CarResponse carResponse=carMapper.toCarResponse(car);
            carResponse.setImageUrls(urls);
            carResponse.setCustomerResponse(customerClient.getCustomer(car.getCustomerId()));
            return carResponse;
        });
    }

    @Override
    public List<CarResponse> getCarsByCustomerId(Long id) {
        List<Car> cars=carRepository.findByCustomerId(id);
        List<CarResponse> carResponses=new ArrayList<>();
        CustomerResponse customerResponse=customerClient.getCustomer(id);
        for(Car car:cars){
            List<String> urls=new ArrayList<>();
            for(ImageUrl imageUrl:car.getImageUrls()){
                urls.add(imageUrl.getImageUrl());
            }
            CarResponse carResponse=carMapper.toCarResponse(car);
            carResponse.setImageUrls(urls);
            carResponse.setCustomerResponse(customerResponse);
            carResponses.add(carResponse);
        }
        return carResponses;
    }
}
