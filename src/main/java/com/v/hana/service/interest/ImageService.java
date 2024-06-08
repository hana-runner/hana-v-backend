package com.v.hana.service.interest;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.v.hana.exception.interest.ImageException;
import com.v.hana.exception.interest.UserInterestDuplicatedException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class ImageService {

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String getImageUrl(MultipartFile file) {
        try {
            String fileName = file.getOriginalFilename();
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(file.getContentType());
            metadata.setContentLength(file.getSize());
            amazonS3Client.putObject(bucket, fileName, file.getInputStream(), metadata);
            return amazonS3Client.getUrl("hanav", fileName).toString();
        } catch(IOException e) {
            e.printStackTrace(); // 개발자들이 어떤 에러인지를 알기 위해 쓰는 것
            throw new ImageException();
        }
    }

    public String getDefaultImage() {
        return amazonS3Client.getUrl("hanav", "default.jpg").toString();
    }

}
