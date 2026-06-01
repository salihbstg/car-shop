package com.bastug.carservice.mapper;

import com.bastug.carservice.dtos.CarResponse;
import com.bastug.carservice.dtos.CreateCarRequest;
import com.bastug.carservice.dtos.UpdateCarRequest;
import com.bastug.carservice.entity.Car;
import org.mapstruct.*;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
public interface CarMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "customerId",ignore = true)
    Car toCar(CreateCarRequest createCarRequest);

    CarResponse toCarResponse(Car save);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    void updateCar(UpdateCarRequest updateCarRequest,@MappingTarget Car car);
}
