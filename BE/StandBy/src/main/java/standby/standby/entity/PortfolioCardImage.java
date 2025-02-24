package standby.standby.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

@Entity
@Setter @Getter
@Table(name="portfolio_card_image")
@NoArgsConstructor
@AllArgsConstructor
public class PortfolioCardImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="portfolio_card_image_id")
    private Long portforlioCardImageId;

    @ManyToOne // Many: 포트폴리오 카드 이미지, One: 포트폴리오 카드
    @JoinColumn(name="portfolio_card_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private PortfolioCard portfolioCard;

    @Column(name="image_path")
    private String imagePath;  // 경로

    @Column(name="ori_name")
    private String oriName;  // 오리지널 이름

    @Column(name="system_name")
    private String systemName;  // 시스템 이름

    @Column(name="created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now(); // 생성날짜(기본값 현재시간)

    @Column(name="updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now(); // 수정날짜(기본값 현재시간)

    @Column(name="deleted_at")
    private LocalDateTime deletedAt;  // 삭제날짜
}
