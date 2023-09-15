package com.zerobase.bob.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AwsS3Service {

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.s3.path}")
    private String bucketPath;

    public String uploadAndGetUrl(MultipartFile file, String path) {

        String uuid = UUID.randomUUID().toString().replaceAll("-","");
        String key = String.format("%s/%s",path.substring(1),uuid);

        ObjectMetadata objectMetaData = new ObjectMetadata();
        objectMetaData.setContentType(file.getContentType());
        objectMetaData.setContentLength(file.getSize());

        try {
            amazonS3.putObject(new PutObjectRequest(bucket, key, file.getInputStream(), objectMetaData)
                                                .withCannedAcl(CannedAccessControlList.PublicRead));
            return amazonS3.getUrl(bucket, key).toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void deleteImageFromS3(String imageUrl) {

        String key = imageUrl.replace(bucketPath,"");
        DeleteObjectRequest request = new DeleteObjectRequest(bucket, key);
        amazonS3.deleteObject(request);
    }
}
