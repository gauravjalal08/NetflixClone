package com.example.apinetflix.Config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;

@Configuration
public class AWSConfig {
    @Value("${aws.accessKey}")
    private String accessKey;
    @Value("${aws.secreatkey}")
    private String secreatkey;

    @Bean
    public AwsCredentialsProvider getAwsCredentials(){
        return StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKey,secreatkey));

    }
}
