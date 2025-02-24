package standby.standby.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;

import java.util.ArrayList;
import java.util.List;

@Entity
@Setter @Getter
@Table(name = "category")
@NoArgsConstructor
@AllArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long categoryId;

    @Column(name = "category_name")
    private String categoryName; // 카테고리 이름

    @Column(name = "is_genre")
    private Boolean isGenre; // 장르

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    private List<CategoryBoard> categoryBoards;


}
