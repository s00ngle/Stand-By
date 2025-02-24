package standby.standby.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import standby.standby.enums.ApplicantStatus;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "user")  // 회원 테이블
@Setter @Getter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;  // 유저 고유 번호

    @Column(name = "kakao_id")
    private Long kakaoId;

    @Column(name="role")   // ADMIN, EMPLOYER, APPLICANT
    private String role;  // 관리자  구인자  지원자

    @Column(name="created_at", updatable = false)
    private LocalDateTime createdAt;  // 생성날짜

    @Column(name="updated_at", updatable = false)
    private LocalDateTime updatedAt;  // 수정날짜

    @Column(name="deleted_at")
    private LocalDateTime deletedAt;  // 삭제날짜

    @Column(name="is_deleted", nullable = false)
    @ColumnDefault("false")
    private Boolean isDeleted = false;  // true: 탈퇴자, false: 미탈퇴자, false 값을 미리 넣어서 미탈퇴자를 디폴트로 함

    @OneToOne(mappedBy = "user")
    private Profile profile;

    @OneToOne(mappedBy = "user")
    private Portfolio portfolio;

    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
    private List<Board> myBoards;

    @OneToMany(mappedBy = "applicant", fetch = FetchType.LAZY)
    private List<ApplicationStatus> applicationStatuses;

    @OneToMany(mappedBy = "employer", fetch = FetchType.LAZY)
    private List<ApplicationStatus> employerStatuses;

    @OneToMany(mappedBy = "evaluator", fetch = FetchType.LAZY)
    private List<Evaluation> evaluationsGiven;

    @OneToMany(mappedBy = "evaluatee", fetch = FetchType.LAZY)
    private List<Evaluation> evaluationsReceived;

}
