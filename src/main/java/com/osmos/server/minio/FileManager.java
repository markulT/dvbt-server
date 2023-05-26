package com.osmos.server.minio;

import com.osmos.server.minio.exceptions.FileManagerException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.InputStream;

public interface FileManager {

    public String uploadFile(MultipartFile file, String bucketName, String objectName) throws FileManagerException;

    InputStream downloadFile(String bucketName, String objectName) throws FileManagerException;

    void deleteFile(String bucketName, String objectName) throws FileManagerException;

    public String getFileLink(String bucketName, String objectName);

}
