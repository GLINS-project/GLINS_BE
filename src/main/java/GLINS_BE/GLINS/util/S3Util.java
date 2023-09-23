package GLINS_BE.GLINS.util;

import GLINS_BE.GLINS.exception.AllGlinsException;
import GLINS_BE.GLINS.exception.ErrorCode;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class S3Util {
    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    // S3에 업로드할 파일을 우선 로컬에 저장 후 S3 서버에 업로드 수행
    public String uploadFiles(MultipartFile multipartFile, String dirName) {
        File uploadFile = convert(multipartFile)  // 파일 변환할 수 없으면 에러
                .orElseThrow(() -> new AllGlinsException(ErrorCode.IMAGE_PROCESSING_FAIL, ErrorCode.IMAGE_PROCESSING_FAIL.getMessage()));
        return upload(uploadFile, dirName);
    }

    // S3에 업로드 후 로컬에 저장된 파일을 삭제하는 함수
    public String upload(File uploadFile, String filePath) {
        String fileName = filePath + "/" + UUID.randomUUID() + uploadFile.getName(); // S3에 저장할 파일의 이름
        String uploadImageUrl = putS3(uploadFile, fileName); // s3로 업로드
        removeNewFile(uploadFile); // 업로드 후 로컬에 존재하는 파일 삭제 처리
        return uploadImageUrl;
    }

    // S3로 업로드하는 함수
    private String putS3(File uploadFile, String fileName) {
        amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, uploadFile).withCannedAcl(CannedAccessControlList.PublicRead));
        return amazonS3Client.getUrl(bucket, fileName).toString(); // 업로드 후 URL 반환
    }

    // 로컬에 저장된 이미지 지우기
    private void removeNewFile(File targetFile) {
        boolean delete = false;
        try {
            delete = targetFile.delete();
        } catch (Exception e) {
            System.out.println("e.getMessage() = " + e.getMessage());
        }
        if (delete) {
            System.out.println("File delete success");
            return;
        }
        System.out.println("File delete fail");
    }

    // 로컬에 파일 업로드 하기
    private Optional<File> convert(MultipartFile file) {
        File convertFile = new File(System.getProperty("user.dir") + "/" + file.getOriginalFilename());
        try {
            if (convertFile.createNewFile()) { // 바로 위에서 지정한 경로에 File이 생성됨 (경로가 잘못 됐다면 생성 불가능)
                FileOutputStream fos = new FileOutputStream(convertFile); // FileOutputStream 데이터를 파일에 바이트 스트림으로 저장하기 위함
                fos.write(file.getBytes());
                fos.close();
                return Optional.of(convertFile);
            }
        } catch (IOException e) {
            throw new Error(e);
        }
        return Optional.empty();
    }
}

