package com.bastug.carservice.dtos;

import com.bastug.carservice.entity.ImageUrl;
import com.bastug.carservice.enums.CarColor;
import com.bastug.carservice.enums.FuelType;
import com.bastug.carservice.enums.TransmissionType;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCarRequest {
    @Pattern(
            regexp = "^(?=.*[A-Za-z])[A-Za-z0-9 ]{2,}$",
            message = "En az 2 karakter giriniz!"
    )
    private String brand;
    @Pattern(
            regexp = "^(?=.*[A-Za-z])[A-Za-z0-9 ]{2,}$",
            message = "En az 2 karakter giriniz!"
    )
    private String model;
    @Min(1980) @Max(2026)
    private Integer year;
    @Pattern(
            regexp = "^[0-9]{2}\\s[A-Z]{1,3}\\s[0-9]{2,4}$",
            message = "Gecersiz plaka formati!"
    )
    private String plate;
    private CarColor carColor;
    private TransmissionType transmissionType;
    private FuelType fuelType;
    private Long customerId;
    private List<ImageUrl> imageUrls=new ArrayList<>();
}

