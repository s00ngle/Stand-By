package standby.standby.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name = "evaluation")
@Setter @Getter
@NoArgsConstructor
@AllArgsConstructor
public class Evaluation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "evaluation_id")
    private Long evaluationId;  // 평가 고유 번호

    @ManyToOne // Many: 평가, One: 평가자
    @JoinColumn(name="evaluator_id")
    private User evaluator;  // 구인자

    @ManyToOne // Many: 평가,  One: 피평가자
    @JoinColumn(name="evaluatee_id")
    private User evaluatee; // 구직자(일을 마친 사람)

    @ManyToOne // Many: 평가, One: 게시글
    @JoinColumn(name="board_id")
    private Board board;  // 게시글

    @Column(name = "comment", columnDefinition = "TEXT")
    private String comment;  // 구직자(일을 마친 사람) 평가 코멘트

    @Column(name = "score1")
    private Double score1;  // 평가항목 1

    @Column(name = "score2")
    private Double score2;  // 평가항목 2

    @Column(name = "score3")
    private Double score3;  // 평가항목 3

    @Column(name = "score4")
    private Double score4;  // 평가항목 4

    @Column(name = "score5")
    private Double score5;  // 평가항목 5

    @Column(name="created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now(); // 생성날짜(기본값 현재시간)

    @Column(name="updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now(); // 수정날짜(기본값 현재시간)

    @Column(name="deleted_at")
    private LocalDateTime deletedAt;  // 삭제날짜


}
