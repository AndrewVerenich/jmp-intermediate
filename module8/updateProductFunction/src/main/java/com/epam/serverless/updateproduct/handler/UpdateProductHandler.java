package com.epam.serverless.updateproduct.handler;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.AttributeUpdate;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.UpdateItemOutcome;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.epam.serverless.updateproduct.ProductRequest;
import com.epam.serverless.updateproduct.ProductResponse;

import java.util.LinkedList;
import java.util.List;


public class UpdateProductHandler implements RequestHandler<ProductRequest, ProductResponse> {
    private DynamoDB dynamoDb;
    private String DYNAMODB_TABLE_NAME = "products";
    private Regions REGION = Regions.US_EAST_1;

    public ProductResponse handleRequest(
            ProductRequest productRequest, Context context) {

        this.initDynamoDbClient();
        updateData(productRequest);
        return new ProductResponse("Product is updated!");
    }

    private UpdateItemOutcome updateData(ProductRequest productRequest)
            throws ConditionalCheckFailedException {

        List<AttributeUpdate> attributeUpdates = new LinkedList<AttributeUpdate>();
        attributeUpdates.add(new AttributeUpdate("name").put((productRequest.getName())));
        attributeUpdates.add(new AttributeUpdate("price").put((productRequest.getPrice())));
        attributeUpdates.add(new AttributeUpdate("pictureUrl").put((productRequest.getPictureUrl())));

        return this.dynamoDb.getTable(DYNAMODB_TABLE_NAME)
                .updateItem(new UpdateItemSpec().withPrimaryKey("Id", productRequest.getId())
                        .withAttributeUpdate(attributeUpdates));
    }

    private void initDynamoDbClient() {
        AmazonDynamoDBClient client = new AmazonDynamoDBClient();
        client.setRegion(Region.getRegion(REGION));
        this.dynamoDb = new DynamoDB(client);
    }
}
