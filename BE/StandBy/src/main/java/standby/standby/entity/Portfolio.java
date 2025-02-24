package standby.standby.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Setter @Getter
@Table(name="portfolio")
@NoArgsConstructor
@AllArgsConstructor
public class Portfolio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="portfolio_id")
    private Long portfolioId;

    @OneToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @Column(name="created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();  // 생성날짜(기본값 현재시간)

    @Column(name="updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();  // 수정날짜(기본값 현재시간)

    @Column(name="deleted_at")
    private LocalDateTime deletedAt;  // 삭제날짜

    @OneToMany(mappedBy = "portfolio", fetch = FetchType.LAZY)
    private List<PortfolioCard> portfolioCards;

}
