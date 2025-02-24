package standby.standby.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "profile")
@Setter @Getter
@NoArgsConstructor
@AllArgsConstructor
public class Profile {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "profile_id")
        private Long profileId;

        @OneToOne
        @JoinColumn(name="user_id")
        private User user;

        @Column(name="nickname")
        private String nickname;

        @Column(name="introduction")
        private String introduction;

        @Column(name = "birth_date")
        private LocalDate birthDate;

        @Column(name="gender")
        private String gender;

        @Column(name="height")
        private Integer height;

        @Column(name="weight")
        private Integer weight;

        @Column(name="sender")
        private String sender;

        @Column(name = "work_counts")
        private String workCounts;

        @Column(name = "work_years")
        private String workYears;


        @OneToMany(mappedBy = "profile", fetch = FetchType.LAZY)
        private List<ProfileImage> profileImages;


}
