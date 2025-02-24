package standby.standby.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Setter @Getter
@Table(name="profile_image")
@NoArgsConstructor
@AllArgsConstructor
public class ProfileImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="profile_image_id")
    private Long profileImageId;

    @ManyToOne
    @JoinColumn(name="profile_id")
    private Profile profile;

    @Column(name="image_path")
    private String imagePath;

    @Column(name="ori_name")
    private String oriName;

    @Column(name="system_name")
    private String systemName;

    @Column(name="created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now(); // 생성날짜(기본값 현재시간)

    @Column(name="updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now(); // 수정날짜(기본값 현재시간)

    @Column(name="deleted_at")
    private LocalDateTime deletedAt;  // 삭제날짜

}
