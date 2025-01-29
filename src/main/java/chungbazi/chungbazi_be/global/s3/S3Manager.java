package chungbazi.chungbazi_be.global.s3;

import chungbazi.chungbazi_be.global.config.S3Confing;
import chungbazi.chungbazi_be.global.entity.Uuid;
import chungbazi.chungbazi_be.global.repository.UuidRepository;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import java.io.IOException;
import java.io.InputStream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Component
@RequiredArgsConstructor
public class S3Manager {
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final AmazonS3 amazonS3;
    private final UuidRepository uuidRepository;

    public String uploadFile(MultipartFile multipartFile, String dirName){
        Uuid savedUuid = Uuid.createAndSave(uuidRepository);

        String originalFileName = multipartFile.getOriginalFilename();
        String uniqueFileName = savedUuid.getUuid() + "_" + (originalFileName != null ? originalFileName.replaceAll("\\s","_") : "unknown");
        String fileName = dirName + "/" + uniqueFileName;

        log.info("Uploading file: {}", fileName);

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(multipartFile.getSize());
        objectMetadata.setContentType(multipartFile.getContentType());

        try(InputStream inputStream = multipartFile.getInputStream()){
            amazonS3.putObject(new PutObjectRequest(bucket, fileName, inputStream, objectMetadata));
        }catch (IOException e){
            log.error("Error uploading file to S3: {}", e.getMessage());
            throw new RuntimeException("파일 업로드에 실패했습니다.", e);
        }
        return amazonS3.getUrl(bucket, fileName).toString();
    }

    public void deleteFile(String fileName) {
        if(fileName == null || fileName.isEmpty()){
            log.warn("fileName is null");
            return;
        }
        try{
            log.info("Deleting file: {}", fileName);
            amazonS3.deleteObject(bucket, fileName);
        } catch (Exception e){
            log.error("Error deleting file from S3: {}", e.getMessage());
            throw new RuntimeException("fail to delete file", e);
        }
    }

}
