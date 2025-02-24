package standby.standby.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter @Getter
@Table(name = "category_application")
@NoArgsConstructor
@AllArgsConstructor
public class CategoryApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_application_id")
    private Long categoryApplicationId;

    @ManyToOne
    @JoinColumn(name="application_status_id")
    private ApplicationStatus applicationStatus;

    @ManyToOne
    @JoinColumn(name="category_id")
    private Category category;
}
