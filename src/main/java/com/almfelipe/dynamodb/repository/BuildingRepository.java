package com.almfelipe.dynamodb.repository;

import com.almfelipe.dynamodb.model.entity.BuildingEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class BuildingRepository {

    @Autowired
    private DynamoDbEnhancedClient dynamoDbEnhancedClient;
    private static final String TABLE_NAME = "building";

    public List<BuildingEntity> findAll() {
        var pageIterable = getTable().scan();
        return pageIterable.items().stream().collect(Collectors.toList());
    }

    public void save(BuildingEntity buildingEntity){
        buildingEntity.setUpdatedAt(LocalDateTime.now());
        this.getTable().putItem(buildingEntity);
    }

    public void delete(BuildingEntity buildingEntity){
        this.delete(buildingEntity.getStreetHashKey(), buildingEntity.getNumberRangeKey());
    }

    public void delete(String hashKey, String rangeKey){
        this.getTable().deleteItem(Key.builder()
                .partitionValue(hashKey)
                .sortValue(rangeKey)
                .build());
    }

    private DynamoDbTable<BuildingEntity> getTable(){
        return dynamoDbEnhancedClient.table(TABLE_NAME, TableSchema.fromBean(BuildingEntity.class));
    }

}
