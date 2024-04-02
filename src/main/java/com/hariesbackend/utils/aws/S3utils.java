package com.hariesbackend.utils.aws;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class S3utils {
    private final AmazonS3 amazonS3;

    @Value("${spring.aws.s3.bucket}")
    private String bucketName;

    public String uploadB64ToS3(String base64Data, String fileName) {
        try {
            byte[] jsonData = Base64.getDecoder().decode(base64Data);

            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(jsonData.length);
            amazonS3.putObject(new PutObjectRequest(bucketName, "dalle/" + fileName, new ByteArrayInputStream(jsonData), metadata));
            return amazonS3.getUrl(bucketName, "dalle/" + fileName).toString();
        } catch (Exception e) {
            System.out.println("S3utils.uploadToS3 error" + e);
            e.printStackTrace();
        }
        return null;
    }
//    private Optional<File> convert
}
