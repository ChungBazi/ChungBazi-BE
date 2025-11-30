package chungbazi.chungbazi_be.global.s3;

import chungbazi.chungbazi_be.global.apiPayload.code.status.ErrorStatus;
import chungbazi.chungbazi_be.global.apiPayload.exception.handler.BadRequestHandler;
import chungbazi.chungbazi_be.global.config.S3Confing;
import chungbazi.chungbazi_be.global.entity.Uuid;
import chungbazi.chungbazi_be.global.repository.UuidRepository;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
    @Value("${cloud.aws.region.static}")
    private String region;

    private final AmazonS3 amazonS3;
    private final UuidRepository uuidRepository;

    public List<String> uploadMultipleFiles(List<MultipartFile> multipartFiles, String dirName) {
        if(multipartFiles == null || multipartFiles.isEmpty()){
            throw new BadRequestHandler(ErrorStatus._BAD_REQUEST);
        }

        List<String> uploadedUrls = new ArrayList<>();
        for (MultipartFile file : multipartFiles) {
            String url = uploadFile(file, dirName);
            uploadedUrls.add(url);
        }
        return uploadedUrls;
    }

    public String uploadFile(MultipartFile multipartFile, String dirName){
        Uuid savedUuid = Uuid.createAndSave(uuidRepository);

        String originalFileName = multipartFile.getOriginalFilename();

        if (originalFileName == null || originalFileName.isEmpty()) {
            throw new BadRequestHandler(ErrorStatus._BAD_REQUEST);
        }

        String uniqueFileName = savedUuid.getUuid() + "_" + originalFileName.replaceAll("\\s","_");
        String fileName = dirName + "/" + uniqueFileName;

        log.info("Uploading file: {}", fileName);

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(multipartFile.getSize());
        objectMetadata.setContentType(multipartFile.getContentType());

        validateImageExtension(originalFileName);

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
            String bucketUrl = "https://" + bucket + ".s3." + region + ".amazonaws.com/"; // 버킷 URL
            if (!fileName.startsWith(bucketUrl)) {
                log.error("Invalid file URL: {}", fileName);
                throw new IllegalArgumentException("잘못된 S3 URL입니다.");
            }

            // URL에서 파일 키 추출
            String fileKey = fileName.substring(bucketUrl.length());
            log.info("Extracted file key: {}", fileKey);

            // 객체 존재 여부 확인
            if (!amazonS3.doesObjectExist(bucket, fileKey)) {
                log.warn("File does not exist in S3: {}", fileKey);
                return; // 파일이 존재하지 않으면 삭제하지 않고 종료
            }

            // DeleteObjectRequest를 사용해 파일 삭제
            amazonS3.deleteObject(new DeleteObjectRequest(bucket, fileKey));
            log.info("Successfully deleted file: {}", fileKey);

            // UUID 엔티티 삭제
            String uuidString = extractUuidFromFileKey(fileKey); // 파일 키에서 UUID 추출
            uuidRepository.deleteByUuid(uuidString);
            log.info("Successfully deleted UUID: {}", uuidString);
        } catch (Exception e){
            log.error("Error deleting file from S3: {}", e.getMessage());
            throw new RuntimeException("fail to delete file", e);
        }
    }
    private String extractUuidFromFileKey(String fileKey) {
        int firstUnderscoreIndex = fileKey.indexOf("_");
        if (firstUnderscoreIndex == -1) {
            throw new IllegalArgumentException("Invalid file key format. UUID not found.");
        }
        return fileKey.substring(fileKey.lastIndexOf("/") + 1, firstUnderscoreIndex);
    }

    public void validateImageExtension(String fileName) {
        int lastDotIndex = fileName.lastIndexOf(".");
        if (lastDotIndex == -1) {
            throw new BadRequestHandler(ErrorStatus.NO_FILE_EXTENTION);
        }
        String extension = fileName.substring(lastDotIndex + 1).toLowerCase();
        List<String> allowedExtensions = Arrays.asList("jpg", "jpeg", "png", "gif");

        if (!allowedExtensions.contains(extension)) {
            throw new BadRequestHandler(ErrorStatus.PICTURE_EXTENSION_ERROR);
        }
    }

}
