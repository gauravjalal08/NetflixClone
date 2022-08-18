package com.example.apinetflix.Accessor;

import org.springframework.beans.factory.annotation.Autowired;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;

import java.time.Duration;

public class S3Accessor {
    private final String bucketName="gsj-netflix-demo";
    @Autowired
    private AwsCredentialsProvider awsCredentialsProvider;
    public String getPreSignedUrl(final String path, final int durationInSecond){
        S3Presigner presigner= S3Presigner.builder()
                .region(Region.AP_SOUTH_1)
                .credentialsProvider(awsCredentialsProvider)
                .build();

        GetObjectRequest getObjectRequest= GetObjectRequest.builder()
                .key(path)
                .bucket(bucketName)
                .build();


        GetObjectPresignRequest getObjectPresignRequest = GetObjectPresignRequest.builder()
                .signatureDuration(Duration.ofSeconds(durationInSecond))
                .getObjectRequest(getObjectRequest)
                .build();
        PresignedGetObjectRequest request=presigner.presignGetObject(getObjectPresignRequest);
        return  request.url().toString();
    }
}
