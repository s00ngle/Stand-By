package standby.standby.service;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import standby.standby.entity.Attachment;
import standby.standby.entity.Board;
import standby.standby.repository.AttachmentRepository;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class AttachementService {
    AttachmentRepository attachmentRepository;
    private final String uploadDir =  "/images/board";
    //        String uploadDir = "c:/images/board";
    public AttachementService(AttachmentRepository attachmentRepository) {
        this.attachmentRepository = attachmentRepository;
    }

    public void CreateAttachment(Board board, List<MultipartFile> files){
        if (files == null || files.isEmpty()) {
            return;
        }

        // 업로드 폴더
        File uploadFolder = new File(uploadDir);
        if (!uploadFolder.exists()) {
            System.out.println("싸기야야야야얗");
            uploadFolder.mkdirs();
        }

        List<Attachment> attachmentList = new ArrayList<>();
        for (MultipartFile file : files) {

                String oriName = file.getOriginalFilename();
            System.out.println(oriName);
                String systemName = UUID.randomUUID().toString()  + "_" + oriName;
            System.out.println(systemName);
                String imagePath = uploadDir + "/" + systemName;
            System.out.println(imagePath);
                File destFile = new File(imagePath);
                try{
                    file.transferTo(destFile);
                    Attachment attachment = new Attachment();
                    attachment.setBoard(board);
                    attachment.setFileName(oriName);
                    attachment.setFilePath(imagePath);
                    attachment.setFileSize(file.getSize());
                    attachment.setFileType(file.getContentType());

                    attachmentList.add(attachment);

            } catch (IOException e) {
                throw new RuntimeException("파일 저장 실패", e);
            }
        }




        attachmentRepository.saveAll(attachmentList);

    }

    public void deleteAttachment(Board board){
        List<Attachment> attachmentList = attachmentRepository.findByBoard(board);

        for (Attachment attachment : attachmentList) {
            try {
                Path filePath = Paths.get(attachment.getFilePath());
                Files.deleteIfExists(filePath); // 파일 존재하면 삭제
            } catch (IOException e) {
                System.err.println("파일 삭제 실패: " + attachment.getFileName());
            }
        }
        attachmentRepository.deleteAll(attachmentList);

    }

    public Resource downloadAttachment(String fileName) {
        try {
            Path filePath = Paths.get(uploadDir).resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            return resource;
        } catch (MalformedURLException e) {
            return null;
        }
    }

    public Resource previewAttachment(String fileName) {
        try {
            Path filePath = Paths.get(uploadDir).resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            return resource;
        } catch (MalformedURLException e) {
            return null;
        }
    }
}




