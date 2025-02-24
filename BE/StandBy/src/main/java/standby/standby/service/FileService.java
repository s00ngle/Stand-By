package standby.standby.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileService {

//    @Value("${file.upload.directory}")  //
    private String uploadDirectory;

    // 파일 업로드 처리
    public String uploadFile(MultipartFile file) {
        try {
            // 업로드 디렉토리 생성
            createDirectoryIfNotExists();

            // 파일 이름 생성 (UUID + 원본 파일명)
            String originalFilename = file.getOriginalFilename();
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String savedFileName = UUID.randomUUID().toString() + extension;

            // 파일 저장 경로
            String filePath = uploadDirectory + File.separator + savedFileName;
            Path path = Paths.get(filePath);

            // 파일 저장
            Files.copy(file.getInputStream(), path);

            // 저장된 파일의 URL 경로 반환
            return "/uploads/" + savedFileName;  // 실제 서비스할 때의 URL 경로

        } catch (IOException e) {
            throw new RuntimeException("파일 업로드 실패: " + e.getMessage());
        }
    }

    // 파일 삭제
    public void deleteFile(String filePath) {
        try {
            // URL 경로에서 파일명 추출
            String fileName = filePath.substring(filePath.lastIndexOf("/") + 1);
            Path path = Paths.get(uploadDirectory + File.separator + fileName);

            // 파일 삭제
            Files.deleteIfExists(path);
        } catch (IOException e) {
            throw new RuntimeException("파일 삭제 실패: " + e.getMessage());
        }
    }

    // 업로드 디렉토리 생성
    private void createDirectoryIfNotExists() {
        File directory = new File(uploadDirectory);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }
}