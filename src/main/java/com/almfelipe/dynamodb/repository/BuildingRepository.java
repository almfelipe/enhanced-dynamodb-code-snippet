package com.almfelipe.dynamodb.repository;

import com.almfelipe.dynamodb.model.entity.BuildingEntity;
import com.almfelipe.dynamodb.utils.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.PutItemEnhancedRequest;
import software.amazon.awssdk.enhanced.dynamodb.model.WriteBatch;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class BuildingRepository {

    @Autowired
    private DynamoDbEnhancedClient dynamoDbEnhancedClient;
    private static final String TABLE_NAME = "building";
    private static final int MAX_BATCH_SIZE_OPERATION = 25;

    public List<BuildingEntity> findAll() {
        var pageIterable = getTable().scan();
        return pageIterable.items().stream().collect(Collectors.toList());
    }

    public void save(List<BuildingEntity> entities) {
        entities.parallelStream().forEach( this::save );
    }

    private void save(BuildingEntity buildingEntity){
        buildingEntity.setUpdatedAt(LocalDateTime.now());
        this.getTable().putItem(buildingEntity);
    }

    public void delete(List<BuildingEntity> entities){
        entities.parallelStream().forEach( this::delete );
    }

    private void delete(BuildingEntity buildingEntity){
        this.delete(buildingEntity.getStreetHashKey(), buildingEntity.getNumberRangeKey());
    }

    private void delete(String hashKey, String rangeKey){
        this.getTable().deleteItem(Key.builder()
                .partitionValue(hashKey)
                .sortValue(rangeKey)
                .build());
    }

    public void writeBatch(List<BuildingEntity> entities) {
        final var partitions = ListUtils.splitList(entities, MAX_BATCH_SIZE_OPERATION);
        partitions.parallelStream().forEach( this::writeBatch25 );
    }

    private void writeBatch25(List<BuildingEntity> entities){
        final var updatedAt = LocalDateTime.now();
        final var wbb = WriteBatch.builder(BuildingEntity.class).mappedTableResource(this.getTable());

        entities.forEach( e-> {
            e.setUpdatedAt(updatedAt);
            wbb.addPutItem(PutItemEnhancedRequest.<BuildingEntity>builder(BuildingEntity.class).item(e).build());
        });

        this.dynamoDbEnhancedClient.batchWriteItem( r-> r.addWriteBatch(wbb.build()));
    }

    private DynamoDbTable<BuildingEntity> getTable(){
        return dynamoDbEnhancedClient.table(TABLE_NAME, TableSchema.fromBean(BuildingEntity.class));
    }

}
