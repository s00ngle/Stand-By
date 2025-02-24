package standby.standby.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "board")
@Setter @Getter
@NoArgsConstructor
@AllArgsConstructor
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long boardId;  // 게시물 고유 번호

    @ManyToOne // Many: 게시글, One: 회원
    @JoinColumn(name="author_id")
    private User author;  // 회원

    @Column(name = "title", length = 255)
    private String title;  // 제목

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;  // 내용

    @Column(name = "start_date")
    private LocalDate startDate;  // 시작 날짜

    @Column(name = "end_date")
    private LocalDate endDate;  // 끝 날짜

    @Column(name = "pay_type", length = 50)
    private String payType;  // 급여 유형

    @Column(name = "pay")
    private int pay;  // 급여

    @Column(name = "duration")
    private Integer duration;  // 급여

    @Column(name = "status")
    private Boolean status = true;  // true: 구인 중, false: 마감

    @Column(name = "available_positions")
    private int availablePositions;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();  // 생성날짜(기본값 현재시간)

    @Column(name="updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();  // 수정날짜(기본값 현재시간)

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;  // 삭제날짜(기본값 현재시간)

    @OneToMany(mappedBy = "board", fetch = FetchType.LAZY)
    private List<ApplicationStatus> applicantStatuses;

    @OneToMany(mappedBy = "board", fetch = FetchType.LAZY)
    private List<CategoryBoard> categoryBoards;

    @OneToMany(mappedBy = "board", fetch = FetchType.LAZY)
    private List<Workplace> workplaces;

    @OneToMany(mappedBy = "board", fetch = FetchType.LAZY)
    private List<Attachment> attachments;

    @OneToMany(mappedBy = "board", fetch = FetchType.LAZY)
    private List<Evaluation> evaluations;

}
