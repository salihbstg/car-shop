package com.bastug.carservice.dtos;

import com.bastug.carservice.entity.ImageUrl;
import com.bastug.carservice.enums.CarColor;
import com.bastug.carservice.enums.FuelType;
import com.bastug.carservice.enums.TransmissionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarResponse {
    private Long id;
    private String brand;
    private String model;
    private Integer year;
    private String plate;
    private CarColor carColor;
    private TransmissionType transmissionType;
    private FuelType fuelType;
    private CustomerResponse customerResponse;
    private List<String> imageUrls=new ArrayList<>();
}
