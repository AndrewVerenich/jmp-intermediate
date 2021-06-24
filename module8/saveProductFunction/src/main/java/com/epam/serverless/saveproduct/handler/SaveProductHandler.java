package com.epam.serverless.saveproduct.handler;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.spec.PutItemSpec;
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.epam.serverless.saveproduct.ProductRequest;
import com.epam.serverless.saveproduct.ProductResponse;

public class SaveProductHandler implements RequestHandler<ProductRequest, ProductResponse> {
    private DynamoDB dynamoDb;
    private String DYNAMODB_TABLE_NAME = "products";
    private Regions REGION = Regions.US_EAST_1;

    public ProductResponse handleRequest(
            ProductRequest productRequest, Context context) {

        this.initDynamoDbClient();
        persistData(productRequest);
        return new ProductResponse("Product is created!");
    }

    private PutItemOutcome persistData(ProductRequest productRequest)
            throws ConditionalCheckFailedException {
        return this.dynamoDb.getTable(DYNAMODB_TABLE_NAME)
                .putItem(
                        new PutItemSpec().withItem(new Item()
                                .withInt("Id", productRequest.getId())
                                .withString("name", productRequest.getName())
                                .withBigDecimalSet("price", productRequest.getPrice())
                                .withString("pictureUrl", productRequest.getPictureUrl())));
    }

    private void initDynamoDbClient() {
        AmazonDynamoDBClient client = new AmazonDynamoDBClient();
        client.setRegion(Region.getRegion(REGION));
        this.dynamoDb = new DynamoDB(client);
    }
}
