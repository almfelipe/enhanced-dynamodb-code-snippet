var params = {
    TableName: 'building',
};
dynamodb.deleteTable(params, function(err, data) {
    if (err) ppJson(err);
    else ppJson(data);
});

var params = {
    TableName: 'building',
    KeySchema: [
        {
            AttributeName: 'addressHashKey',
            KeyType: 'HASH',
        },
        {
            AttributeName: 'numberRangeKey',
            KeyType: 'RANGE',
        }
    ],
    AttributeDefinitions: [
        {
            AttributeName: 'addressHashKey', //salvador#ba#costa azul#rua doutor autgusto lopes pontes
            AttributeType: 'S', // (S | N | B) for string, number, binary
        },
        {
            AttributeName: 'numberRangeKey', //477
            AttributeType: 'S', // (S | N | B) for string, number, binary
        },
        {
            AttributeName: 'buildingType', // house, building
            AttributeType: 'S', // (S | N | B) for string, number, binary
        },
        {
            AttributeName: 'buildDate', //
            AttributeType: 'S', // (S | N | B) for string, number, binary
        },
        {
            AttributeName: 'area', //120
            AttributeType: 'N', // (S | N | B) for string, number, binary
        },
        {
            AttributeName: 'price', //120
            AttributeType: 'N', // (S | N | B) for string, number, binary
        },
    ],
    ProvisionedThroughput: {
        ReadCapacityUnits: 1,
        WriteCapacityUnits: 1,
    },
    GlobalSecondaryIndexes: [
        {
            IndexName: 'GSI_building_type',
            KeySchema: [
                { // Required HASH type attribute
                    AttributeName: 'buildingType',
                    KeyType: 'HASH',
                },
            ],
            Projection: {
                ProjectionType: 'ALL', // (ALL | KEYS_ONLY | INCLUDE)
            },
            ProvisionedThroughput: {
                ReadCapacityUnits: 1,
                WriteCapacityUnits: 1,
            },
        },
        {
            IndexName: 'GSI_build_date',
            KeySchema: [
                { // Required HASH type attribute
                    AttributeName: 'buildDate',
                    KeyType: 'HASH',
                },
            ],
            Projection: {
                ProjectionType: 'ALL', // (ALL | KEYS_ONLY | INCLUDE)
            },
            ProvisionedThroughput: {
                ReadCapacityUnits: 1,
                WriteCapacityUnits: 1,
            },
        },
        {
            IndexName: 'GSI_area',
            KeySchema: [
                { // Required HASH type attribute
                    AttributeName: 'area',
                    KeyType: 'HASH',
                },
            ],
            Projection: {
                ProjectionType: 'ALL', // (ALL | KEYS_ONLY | INCLUDE)
            },
            ProvisionedThroughput: {
                ReadCapacityUnits: 1,
                WriteCapacityUnits: 1,
            },
        },
        {
            IndexName: 'GSI_price',
            KeySchema: [
                { // Required HASH type attribute
                    AttributeName: 'price',
                    KeyType: 'HASH',
                },
            ],
            Projection: {
                ProjectionType: 'ALL', // (ALL | KEYS_ONLY | INCLUDE)
            },
            ProvisionedThroughput: {
                ReadCapacityUnits: 1,
                WriteCapacityUnits: 1,
            },
        },
    ],
};
dynamodb.createTable(params, function(err, data) {
    if (err) ppJson(err); // an error occurred
    else ppJson(data); // successful response
});