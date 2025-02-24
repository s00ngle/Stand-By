package standby.standby.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import standby.standby.enums.ApplicantStatus;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Setter @Getter
@Table(name="application_status")
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationStatus {  // 지원자 상태
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="application_status_id")
    private Long applicationStatusId;   // 지원 고유 번호

    @ManyToOne
    @JoinColumn(name = "employer_id") // 외래 키 지정
    private User employer;  // Many: 지원자 상태, One: 구인자

    @ManyToOne
    @JoinColumn(name = "applicant_id") // 외래 키 지정
    private User applicant; // Many: 지원자 상태, One: 지원자

    @ManyToOne
    @JoinColumn(name = "board_id") // 외래 키 지정
    private Board board; // Many: 지원자 상태, One: 게시물

    @Enumerated(EnumType.STRING)
    @Column(name="applicant_status")
    private ApplicantStatus applicantStatus; // 지원 상태: 지원완료, 면접합격, 최종합격, 불합격

    @Column(name="memo", columnDefinition = "TEXT")
    private String memo;  // 모든 지원자(지원완료, 면접합격, 최종합격, 불합격) 메모

    @Column(name="created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();  // 생성날짜(기본값 현재시간)

    @Column(name="updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();  // 수정날짜(기본값 현재시간)

    @Column(name="deleted_at")
    private LocalDateTime deletedAt;  // 삭제날짜


}
