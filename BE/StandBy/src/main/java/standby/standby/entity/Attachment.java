package standby.standby.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Setter @Getter
@Table(name="attachment")
@NoArgsConstructor
@AllArgsConstructor
public class Attachment {  // 게시물 첨부파일

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="attachment_id")
    private Long attachmentId;  // 게시물 고유 번호

    @ManyToOne // Many: 첨부파일, One: 게시글
    @JoinColumn(name="board_id")
    private Board board;   // 게시글

    @Column(name="file_name")
    private String fileName;   // 파일이름

    @Column(name="file_path")
    private String filePath;  // 파일경로

    @Column(name="file_size")
    private long fileSize;  // 파일크기

    @Column(name="file_type")
    private String fileType; // 파일유형

    @Column(name="created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();  // 생성날짜(기본값 현재시간)

    @Column(name="updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();  // 수정날짜(기본값 현재시간)

    @Column(name="deleted_at")
    private LocalDateTime deletedAt;  // 삭제날짜



}
