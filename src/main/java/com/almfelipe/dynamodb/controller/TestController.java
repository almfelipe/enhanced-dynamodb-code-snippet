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
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@RestController
public class TestController {

    @Autowired
    private BuildingRepository repository;

    private void clearDb(){
        final var buildingEntityList = repository.findAll();
        buildingEntityList.forEach(buildingEntity -> repository.delete(buildingEntity));
    }

    private List<BuildingEntity> getInitialBuildings(){
        final var buildingEntityList = new ArrayList<BuildingEntity>();

//        buildingEntityList.add(BuildingEntity.builder()
//                .streetHashKey("salvador#ba#costa azul#rua doutor augusto lopes pontes")
//                .numberRangeKey("477")
//                .buildingType(BuildingType.RESIDENTIAL)
//                .buildDate(LocalDate.now().minusYears(10))
//                .forSale(true)
//                .area(70)
//                .price(BigDecimal.valueOf(225000.40))
//                .build());
//
//        buildingEntityList.add(BuildingEntity.builder()
//                .streetHashKey("salvador#ba#pituba#avenida paulo vi")
//                .numberRangeKey("2200")
//                .buildingType(BuildingType.RESIDENTIAL)
//                .buildDate(LocalDate.now().minusYears(3))
//                .forSale(false)
//                .area(120)
//                .price(BigDecimal.valueOf(800000.40))
//                .build());
//
//        buildingEntityList.add(BuildingEntity.builder()
//                .streetHashKey("petrolina#pe#vale dourado#rua dom tomaz")
//                .numberRangeKey("80")
//                .buildingType(BuildingType.RESIDENTIAL)
//                .buildDate(LocalDate.now().minusYears(5))
//                .forSale(false)
//                .area(200)
//                .price(BigDecimal.valueOf(290000.00))
//                .build());

        for(int i = 0; i < 100; i++){
            buildingEntityList.add(BuildingEntity.builder()
                    .streetHashKey("petrolina#pe#vale dourado#rua dom tomaz" + i)
                    .numberRangeKey("80")
                    .buildingType(BuildingType.RESIDENTIAL)
                    .buildDate(LocalDate.now().minusYears(5))
                    .forSale(false)
                    .area(200)
                    .price(BigDecimal.valueOf(290000.00))
                    .build());
        }

        return buildingEntityList;
    }

    @GetMapping("/start")
    public ResponseEntity<String> start(){

        final var buildingEntityList = this.getInitialBuildings();

        this.clearDb();
        var startWrite = LocalDateTime.now();
        repository.writeBatch(buildingEntityList);
        var endWrite = LocalDateTime.now();
        final var batchTime = ChronoUnit.NANOS.between(startWrite, endWrite);

        var retVal = batchTime + " --->batch";

        this.clearDb();
        startWrite = LocalDateTime.now();
        buildingEntityList.forEach( e -> repository.save(e));
        endWrite = LocalDateTime.now();
        final var saveTime = ChronoUnit.NANOS.between(startWrite, endWrite);

        retVal = retVal + "<br>" + saveTime + " --->single";

        if(saveTime > batchTime){
            retVal = retVal + "<br> bach mais rápido: " + (batchTime * 100 / saveTime) + "%";
        }else if(saveTime < batchTime){
            retVal = retVal + "<br> single mais rápido: " + (saveTime * 100 /batchTime) + "%";
        }else{
            retVal = retVal + "<br> empate";
        }

        return new ResponseEntity<>(retVal, HttpStatus.OK) ;
    }

    @GetMapping("findall")
    public ResponseEntity<List<BuildingEntity>> findAll(){
        return new ResponseEntity<>(repository.findAll(), HttpStatus.OK) ;
    }

}
