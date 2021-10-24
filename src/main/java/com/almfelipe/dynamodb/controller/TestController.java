package com.almfelipe.dynamodb.controller;

import com.almfelipe.dynamodb.model.entity.BuildingEntity;
import com.almfelipe.dynamodb.model.type.BuildingType;
import com.almfelipe.dynamodb.repository.BuildingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
public class TestController {

    @Autowired
    private BuildingRepository repository;

    @GetMapping("/start")
    public ResponseEntity<List<BuildingEntity>> start(){

        final var buildingEntityList = repository.findAll();
        buildingEntityList.forEach(buildingEntity -> repository.delete(buildingEntity));

        repository.save(BuildingEntity.builder()
                        .streetHashKey("salvador#ba#costa azul#rua doutor augusto lopes pontes")
                        .numberRangeKey("477")
                        .buildingType(BuildingType.RESIDENTIAL.name())
                        .buildDate(LocalDate.now().toString())
                        .forSale(true)
                        .area(70)
                        .price(BigDecimal.valueOf(225000.40))
                .build());

        repository.save(BuildingEntity.builder()
                .streetHashKey("salvador#ba#pituba#avenida paulo vi")
                .numberRangeKey("2200")
                .buildingType(BuildingType.RESIDENTIAL.name())
                .buildDate(LocalDate.now().toString())
                .forSale(false)
                .area(120)
                .price(BigDecimal.valueOf(800000.40))
                .build());

        repository.save(BuildingEntity.builder()
                .streetHashKey("petrolina#pe#vale dourado#rua dom tomaz")
                .numberRangeKey("80")
                .buildingType(BuildingType.RESIDENTIAL.name())
                .buildDate(LocalDate.now().toString())
                .forSale(false)
                .area(120)
                .price(BigDecimal.valueOf(290000.00))
                .build());

        return this.findAll();
    }

    @GetMapping("findall")
    public ResponseEntity<List<BuildingEntity>> findAll(){
        return new ResponseEntity<>(repository.findAll(), HttpStatus.OK) ;
    }

}
