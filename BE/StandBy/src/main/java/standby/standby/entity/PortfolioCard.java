package standby.standby.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Setter @Getter
@Table(name="portfolio_card")
@NoArgsConstructor
@AllArgsConstructor
public class PortfolioCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "portfolio_card_id")
    private Long portfolioCardId;    // 포트폴리오 카드 고유번호

    @ManyToOne // Many: 평가, One: 게시글
    @JoinColumn(name = "portfolio_id")
    private Portfolio portfolio;  // 포트폴리오

    @Column(name = "type_card")
    private Long typeCard;   // 포트폴리오 카드 유형(1: 유튜브, 2: 사진)

    @Column(name = "title")
    private String title;   // 포트폴리오 카드 제목

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;  // 포트폴리오 카드 내용

    @Column(name="created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now(); // 생성날짜(기본값 현재시간)

    @Column(name="updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now(); // 수정날짜(기본값 현재시간)

    @Column(name="deleted_at")
    private LocalDateTime deletedAt;  // 삭제날짜

    @Column(name="youtubeUrl")
    private String youtubeUrl;

    @OneToMany(mappedBy = "portfolioCard", fetch = FetchType.LAZY)
    private List<PortfolioCardImage> portfolioCardImage;


}
