package com.almfelipe.dynamodb.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.*;

import java.math.BigDecimal;

@Builder
@Setter
@AllArgsConstructor
@NoArgsConstructor
@DynamoDbBean
public class BuildingEntity {

    private String streetHashKey;
    private String numberRangeKey;
    private String buildingType;
    private String buildDate;
    private boolean forSale;
    private int area;
    private BigDecimal price;

    @DynamoDbPartitionKey
    @DynamoDbAttribute("streetHashKey")
    public String getStreetHashKey() {
        return streetHashKey;
    }

    public void setStreetHashKey(String streetHashKey) {
        this.streetHashKey = streetHashKey;
    }

    @DynamoDbSortKey
    @DynamoDbAttribute("numberRangeKey")
    public String getNumberRangeKey() {
        return numberRangeKey;
    }

    public void setNumberRangeKey(String numberRangeKey) {
        this.numberRangeKey = numberRangeKey;
    }

    @DynamoDbSecondaryPartitionKey(indexNames = "GSI_building_type")
    @DynamoDbAttribute("buildingType")
    public String getBuildingType() {
        return buildingType;
    }

    public void setBuildingType(String buildingType) {
        this.buildingType = buildingType;
    }

    @DynamoDbSecondaryPartitionKey(indexNames = "GSI_build_date")
    @DynamoDbAttribute("buildDate")
    public String getBuildDate() { return buildDate; }

    public void setBuildDate(String buildDate) {
        this.buildDate = buildDate;
    }

    @DynamoDbAttribute("forSale")
    public boolean getForSale() {
        return forSale;
    }

    public void setForSale(boolean forSale) {
        this.forSale = forSale;
    }

    @DynamoDbSecondaryPartitionKey(indexNames = "GSI_area")
    @DynamoDbAttribute("area")
    public int getArea() {
        return area;
    }

    public void setArea(int area) {
        this.area = area;
    }

    @DynamoDbSecondaryPartitionKey(indexNames = "GSI_price")
    @DynamoDbAttribute("price")
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
