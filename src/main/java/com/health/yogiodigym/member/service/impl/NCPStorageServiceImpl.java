package com.health.yogiodigym.member.service.impl;

import com.health.yogiodigym.member.service.NCPStorageService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NCPStorageServiceImpl implements NCPStorageService {
    private final S3Client s3Client;

    @Value("${cloud.ncp.storage.bucket}")
    private String bucketName;

    @Value("${cloud.ncp.storage.endpoint}")
    private String endpoint;

    @Override
    public String uploadImage(MultipartFile file, DirectoryPath directory) {
        if(file.isEmpty()) {
            return null;
        }

        String key = directory.getMessage() + "/" + UUID.randomUUID() + "_" + file.getOriginalFilename();

        try {
            s3Client.putObject(PutObjectRequest.builder()
                            .bucket(bucketName)
                            .key(key)
                            .acl(ObjectCannedACL.PUBLIC_READ)
                            .contentType(file.getContentType())
                            .build(),
                    software.amazon.awssdk.core.sync.RequestBody.fromByteBuffer(ByteBuffer.wrap(file.getBytes()))
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return String.format("%s/%s/%s", endpoint, bucketName, key);
    }

    @Override
    public void deleteImageByUrl(String fileUrl) {
        String fileKey = extractFileKey(fileUrl);

        s3Client.deleteObject(DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(fileKey)
                .build());
    }

    private String extractFileKey(String fileUrl) {
        return fileUrl.replace(endpoint + "/" + bucketName + "/", "");
    }

    @Getter
    @RequiredArgsConstructor
    public enum DirectoryPath {
        PROFILE("profile"),
        CERTIFICATE("certificate");

        private final String message;
    }
}
