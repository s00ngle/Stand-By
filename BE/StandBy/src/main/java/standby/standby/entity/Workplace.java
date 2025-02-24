package standby.standby.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter @Getter
@Table(name="workplace")
@NoArgsConstructor
@AllArgsConstructor
public class Workplace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "workplace_id")
    private Long workplaceId;   // 일하는 장소

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;   // 게시물 고유 번호

    @Column(name = "place_name")
    private String placeName;   // 장소 이름

    @Column(name = "place_detail")
    private String placeDetail;   // 장소 상세정보

    @Column(name = "longitude")
    private Double longitude;   // 경도

    @Column(name = "latitude")
    private Double latitude;   // 위도


}