package com.bastug.carservice.entity;

import com.bastug.carservice.enums.CarColor;
import com.bastug.carservice.enums.FuelType;
import com.bastug.carservice.enums.TransmissionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cars")
@Data
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String brand;
    private String model;
    private Integer year;
    @Column(unique = true, nullable = false)
    private String plate;

    @Enumerated(EnumType.STRING)
    private CarColor carColor;
    @Enumerated(EnumType.STRING)
    private TransmissionType transmissionType;
    @Enumerated(EnumType.STRING)
    private FuelType fuelType;

    @Column(nullable = false)
    private Long customerId;

    @OneToMany(
            mappedBy = "car",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    List<ImageUrl> imageUrls=new ArrayList<>();
}
