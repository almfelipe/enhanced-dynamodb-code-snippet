package com.almfelipe.dynamodb.model.entity;

import com.almfelipe.dynamodb.model.type.BuildingType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@Setter
@AllArgsConstructor
@NoArgsConstructor
@DynamoDbBean
public class BuildingEntity {

    private String streetHashKey;
    private String numberRangeKey;
    private BuildingType buildingType;
    private LocalDate buildDate;
    private boolean forSale;
    private int area;
    private BigDecimal price;
    private LocalDateTime updatedAt;

    @DynamoDbPartitionKey
    @DynamoDbAttribute("streetHashKey")
    public String getStreetHashKey() {
        return streetHashKey;
    }

    @DynamoDbSortKey
    @DynamoDbAttribute("numberRangeKey")
    public String getNumberRangeKey() {
        return numberRangeKey;
    }

    @DynamoDbSecondaryPartitionKey(indexNames = "GSI_building_type")
    @DynamoDbAttribute("buildingType")
    public BuildingType getBuildingType() {
        return buildingType;
    }

    @DynamoDbSecondaryPartitionKey(indexNames = "GSI_build_date")
    @DynamoDbAttribute("buildDate")
    public LocalDate getBuildDate() { return buildDate; }

    @DynamoDbAttribute("forSale")
    public boolean getForSale() {
        return forSale;
    }

    @DynamoDbSecondaryPartitionKey(indexNames = "GSI_area")
    @DynamoDbAttribute("area")
    public int getArea() {
        return area;
    }

    @DynamoDbSecondaryPartitionKey(indexNames = "GSI_price")
    @DynamoDbAttribute("price")
    public BigDecimal getPrice() {
        return price;
    }

    @DynamoDbAttribute("updatedAt")
    public LocalDateTime getUpdatedAt() { return this.updatedAt; }
}
