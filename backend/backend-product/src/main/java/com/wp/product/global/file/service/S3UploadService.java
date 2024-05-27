package com.wp.product.global.file.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.wp.product.global.common.code.ErrorCode;
import com.wp.product.global.exception.BusinessExceptionHandler;
import com.wp.product.global.file.dto.CustomMultipartFile;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class S3UploadService {

    private final AmazonS3 amazonS3;
    static final int TARGET_HEIGHT = 1000;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    public String makeDir(){
        String folderPath = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));

        amazonS3.putObject(bucket, folderPath + "/", new ByteArrayInputStream(new byte[0]), new ObjectMetadata());
        return folderPath;
    }

    public MultipartFile resizeFile(String fileName,String fileFormatName,MultipartFile multipartFile) {
        try {
            BufferedImage sourceImage = ImageIO.read(multipartFile.getInputStream());

            if(sourceImage.getHeight() > TARGET_HEIGHT) {
                //동일 비율로 리사이징하기 위함
                double sourceImageRatio = (double) sourceImage.getWidth() / sourceImage.getHeight();
                int newWidth = (int) (TARGET_HEIGHT * sourceImageRatio);

                //이미지 사이즈 변경
                sourceImage = Scalr.resize(sourceImage, newWidth, TARGET_HEIGHT);

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(sourceImage, fileFormatName, baos);
                baos.flush();
                byte[] bytes = baos.toByteArray();

                return new CustomMultipartFile(bytes, "image", fileName, fileFormatName, bytes.length);
            }
            return multipartFile;
        }catch (IOException e){
            throw new BusinessExceptionHandler("파일 리사이징 실패",ErrorCode.FAIL_FILE_UPLOAD);
        }
    }

    public String saveFile(MultipartFile multipartFile) {
        if(multipartFile!= null && !multipartFile.isEmpty()) {
            String folderPath = makeDir();
            String uuid = UUID.randomUUID().toString();
            String originalFilename = uuid+multipartFile.getOriginalFilename();
            String fileFormatName = multipartFile.getContentType().substring(multipartFile.getContentType().lastIndexOf("/") + 1);

            //파일 확장자 확인
            validateFileExtension(multipartFile.getOriginalFilename());

            log.info("원본 파일명 : "+multipartFile.getOriginalFilename());
            log.info("파일명 : "+originalFilename);
            log.info("파일 타입 : " +multipartFile.getContentType());
            log.info("파일 크기 : " +multipartFile.getSize());

            MultipartFile resizedFile = resizeFile(multipartFile.getOriginalFilename(),fileFormatName,multipartFile);
            log.info("리사이징된 파일 타입 : " +resizedFile.getContentType());
            log.info("리사이징된 파일 크기 : " +resizedFile.getSize());

            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(resizedFile.getSize());
            metadata.setContentType(resizedFile.getContentType());

            try {
                amazonS3.putObject(bucket + "/" + folderPath, originalFilename, resizedFile.getInputStream(), metadata);
            }catch (Exception e){
                log.info(e.getMessage());
                throw new BusinessExceptionHandler("파일 업로드 중 에러 발생", ErrorCode.FAIL_FILE_UPLOAD);
            }
            return amazonS3.getUrl(bucket+"/"+folderPath, originalFilename).toString();
        }
        return "";
    }

    public void deleteImage(String imgsrc)  {
        try {
            if(imgsrc !=null && !"".equals(imgsrc)) {
//            String path = imgsrc.substring(0,imgsrc.lastIndexOf(bucket)+);
            String fileName = imgsrc.substring(imgsrc.lastIndexOf(bucket)+bucket.length()+1);
//            System.out.println(path);
            System.out.println(fileName);

            amazonS3.deleteObject(bucket, fileName);
            }
        }catch (Exception e){
            log.debug(e.getMessage());
            throw new BusinessExceptionHandler("사진 삭제 중 에러 발생", ErrorCode.FAIL_FILE_DELETE);
        }
    }

    // 파일 확장자 체크
    private void validateFileExtension(String originalFilename) {
        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1).toLowerCase();
        List<String> allowedExtensions = Arrays.asList("jpg", "png", "jpeg");

        if (!allowedExtensions.contains(fileExtension)) {
            throw new BusinessExceptionHandler("지원하지 않는 파일 확장자입니다", ErrorCode.NOT_IMAGE_EXTENSION);
        }
    }
}
