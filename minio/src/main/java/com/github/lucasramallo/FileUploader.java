package com.github.lucasramallo;

import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.UploadObjectArgs;
import io.minio.errors.MinioException;
import io.minio.http.Method;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class FileUploader {
    public static void main(String[] args)
            throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        try {
            // Create a minioClient with the MinIO server playground, its access key and secret key.
            MinioClient minioClient =
                    MinioClient.builder()
                            .endpoint("http://10.12.129.190:9000")
                            .credentials("Aa5xAIUbMHcsPCGAz5dJ", "amaPaEv0AkvoOvOr7iINOAwOCVH8JXzBXzFHlRKs")
                            .build();

            // Upload '/home/user/Photos/asiaphotos.zip' as object name 'asiaphotos-2015.zip' to bucket
            minioClient.uploadObject(
                    UploadObjectArgs.builder()
                            .bucket("bd-2")
                            .object("file.pdf")
                            .filename("src/main/resources/teste.pdf")
                            .build());
            System.out.println(
                    "'/home/user/Photos/asiaphotos.zip' is successfully uploaded as "
                            + "object 'asiaphotos-2015.zip' to bucket 'asiatrip'.");

            String url = minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                    .method(Method.GET)
                    .bucket("bd-2")
                    .object("file.pdf")
                    .expiry(60 * 60 * 24)
                    .build());

            System.out.println(url);
        } catch (MinioException e) {
            System.out.println("Error occurred: " + e);
            System.out.println("HTTP trace: " + e.httpTrace());
        }
    }
}