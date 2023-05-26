package com.osmos.server.minio;

import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MinioConfiguration {

    @Value("${s3.minio.url}")
    private String minioUrl;


    @Value("${s3.minio.secretKey}")
    private String secretKey;

    @Value("${s3.minio.accessKey}")
    private String accessKey;

    @Bean
    public MinioClient minioClient() {
        System.out.println(accessKey + " " + secretKey);
        return MinioClient.builder()
                .endpoint(minioUrl)
                .credentials(accessKey, secretKey)
                .build();
    }

}
