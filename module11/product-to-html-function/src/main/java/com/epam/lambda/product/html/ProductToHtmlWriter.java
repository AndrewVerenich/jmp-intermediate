package com.epam.lambda.product.html;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.StreamRecord;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.DynamodbEvent;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.stream.Stream;

public class ProductToHtmlWriter implements RequestHandler<DynamodbEvent, Void> {

    public static final String BUCKET = "products-module11";

    public Void handleRequest(DynamodbEvent dynamodbEvent, Context context) {
        for (DynamodbEvent.DynamodbStreamRecord record : dynamodbEvent.getRecords()) {
            if (record == null) {
                continue;
            }
            StreamRecord recordDynamodb = record.getDynamodb();
            Map<String, AttributeValue> attributeValueMap = recordDynamodb.getKeys();
            String htmlTemplate = readContent("productTemplate.html");
            String orderHtmlContent = fillTemplate(htmlTemplate, attributeValueMap);
            saveProductHtmlToS3(orderHtmlContent, "Product_" + attributeValueMap.get("Id").getS() + ".html");
        }
        return null;
    }

    private static String readContent(String filePath) {
        StringBuilder contentBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines(Paths.get(filePath), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s).append("\n"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return contentBuilder.toString();
    }

    private static String fillTemplate(String template, Map<String, AttributeValue> map) {
        return template
                .replace("$Id", map.get("Id").getS())
                .replace("$name", map.get("name").getS())
                .replace("$price", map.get("price").getS())
                .replace("$pictureUrl", map.get("pictureUrl").getS());
    }

    private static void saveProductHtmlToS3(String content, String fileName) {
        AmazonS3 client = new AmazonS3Client();
        ByteArrayInputStream inputStream = new ByteArrayInputStream(content.getBytes());
        client.putObject(BUCKET, fileName, inputStream, new ObjectMetadata());
    }
}