package com.bastug.carservice.dtos;

import com.bastug.carservice.entity.ImageUrl;
import com.bastug.carservice.enums.CarColor;
import com.bastug.carservice.enums.FuelType;
import com.bastug.carservice.enums.TransmissionType;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCarRequest {
    @NotBlank
    private String brand;
    @NotBlank
    private String model;
    @Min(value = 1980,message = "1980-2026 aralığında olabilir")
    @Max(value = 2026,message = "1980-2026 aralığında olabilir")
    @NotNull
    private Integer year;
    @NotBlank(message = "Plakayı kontrol edin!")
    @Pattern(
            regexp = "^[0-9]{2}\\s[A-Z]{1,3}\\s[0-9]{2,4}$",
            message = "Gecersiz plaka formati!"
    )
    private String plate;
    @NotNull(message = "Renk seçimi yapınız!")
    private CarColor carColor;
    @NotNull(message = "Vites seçimi yapınız!")
    private TransmissionType transmissionType;
    @NotNull(message = "Yakıt seçimi yapınız!")
    private FuelType fuelType;

    private List<String> imageUrls=new ArrayList<>();
}
