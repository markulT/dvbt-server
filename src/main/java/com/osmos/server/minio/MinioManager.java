package com.osmos.server.minio;

import com.osmos.server.minio.exceptions.FileManagerException;
import io.minio.*;
import io.minio.errors.*;
import io.minio.http.Method;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class MinioManager implements FileManager {

    private final MinioClient minioClient;

    @Override
    public String uploadFile(MultipartFile file, String bucketName, String objectName) throws FileManagerException {
        try {
            minioClient.putObject(PutObjectArgs.builder()
                    .contentType(file.getContentType())
                            .bucket(bucketName)
                            .object(objectName)
                            .stream(file.getInputStream(), file.getSize(), -1)
                    .build());
            return minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .method(Method.GET)
                            .expiry(24 , TimeUnit.HOURS)
                    .build());
        } catch (Exception e) {
            throw new FileManagerException("Error while uploading file", e);
        }
    }

    @Override
    public InputStream downloadFile(String bucketName, String objectName) throws FileManagerException {
        try {
            return minioClient.getObject(GetObjectArgs.builder()
                    .bucket(bucketName)
                    .object(objectName)
                    .build());
        } catch (Exception e) {
            throw new FileManagerException("Error while downloading file", e);
        }
    }

    @Override
    public void deleteFile(String bucketName, String objectName) throws FileManagerException {
        try {
            minioClient.removeObject(RemoveObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                    .build());
        } catch (Exception e) {
            throw new FileManagerException("Failed to delete file from MinIO", e);
        }
    }

    @Override
    public String getFileLink(String bucketName, String objectName) throws FileManagerException {
        try {
            return minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .method(Method.GET)
                            .expiry(24, TimeUnit.HOURS)
                    .build());
        } catch (Exception e) {
            throw new FileManagerException("Failed to get object url",e);
        }
    }
}
