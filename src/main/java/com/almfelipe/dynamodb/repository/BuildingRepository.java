package com.almfelipe.dynamodb.repository;

import com.almfelipe.dynamodb.model.entity.BuildingEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class BuildingRepository {

    @Autowired
    private DynamoDbEnhancedClient dynamoDbEnhancedClient;

    public List<BuildingEntity> findAll() {
        var pageIterable = getTable().scan();
        return pageIterable.items().stream().collect(Collectors.toList());
    }

    public void save(BuildingEntity buildingEntity){
        try {
            this.getTable().putItem(buildingEntity);
        }catch(Exception e){
         e.printStackTrace();
        }
    }

    public void delete(BuildingEntity buildingEntity){
        this.getTable().deleteItem(Key.builder()
                .partitionValue(buildingEntity.getStreetHashKey())
                .sortValue(buildingEntity.getNumberRangeKey())
                .build());
    }

    private DynamoDbTable<BuildingEntity> getTable(){
        return dynamoDbEnhancedClient.table("building", TableSchema.fromBean(BuildingEntity.class));
    }

}
