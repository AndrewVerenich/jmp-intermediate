package com.epam.messaging.thirdapp;

import com.amazonaws.services.s3.AmazonS3;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
@AllArgsConstructor
public class S3FileUploader {

    private final AmazonS3 s3;
    private static final String BUCKET = "orders-bucket-module10";

    public void uploadOrdersFile() {
        File localFile = new File("orders/orders.txt");
        s3.putObject(BUCKET, "orders.txt", localFile);
    }
}
