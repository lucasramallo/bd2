package com.github.lucasramallo;

import io.minio.DownloadObjectArgs;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.UploadObjectArgs;
import io.minio.errors.*;
import io.minio.http.Method;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class ObjectStorageService {
    private final MinioClient minioClient;

    public ObjectStorageService() {
        this.minioClient =
                MinioClient.builder()
                        .endpoint("http://192.168.1.13:9000")
                        .credentials("5VLlbnm8LEJHE1hvZ7IY", "gLR2OfYBNDzqWo1VzIe1K4WY9MngRvPGiirrZngN")
                        .build();
    }

    public void upload(String bucketName, String objetcName, String filePath) {
        try {
            this.minioClient.uploadObject(
                    UploadObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objetcName)
                            .filename(filePath)
                            .build());
            System.out.println(filePath + " is successfully uploaded as " + objetcName +  "to bucket" + bucketName);
        } catch (MinioException e) {
            System.out.println("Error occurred: " + e);
            System.out.println("HTTP trace: " + e.httpTrace());
        } catch (InvalidKeyException | NoSuchAlgorithmException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void getObjectUrl(String bucketName, String objetcName) {
        try {
            String url = minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                    .method(Method.GET)
                    .bucket(bucketName)
                    .object(objetcName)
                    .expiry(60 * 60 * 24)
                    .build());

            System.out.println(url);
        } catch (MinioException e) {
            System.out.println("Error occurred: " + e);
            System.out.println("HTTP trace: " + e.httpTrace());
        } catch (IOException | InvalidKeyException | NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public void dowloadObject(String bucketName, String objetcName, String destination) {
        try {
            DownloadObjectArgs args = DownloadObjectArgs.builder()
                    .bucket(bucketName)
                    .object(objetcName)
                    .filename(resolveUniqueFileName(destination, objetcName))
                    .build();
            this.minioClient.downloadObject(args);
            System.out.println("downloaded");
            minioClient.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String resolveUniqueFileName(String directory, String originalFileName) {
        Path filePath = Paths.get(directory, originalFileName);
        String fileName = filePath.getFileName().toString();
        String baseName = fileName.contains(".") ? fileName.substring(0, fileName.lastIndexOf('.')) : fileName;
        String extension = fileName.contains(".") ? fileName.substring(fileName.lastIndexOf('.')) : "";

        int counter = 1;
        while (filePath.toFile().exists()) {
            filePath = Paths.get(directory, baseName + "(" + counter + ")" + extension);
            counter++;
        }

        return filePath.toString();
    }
}