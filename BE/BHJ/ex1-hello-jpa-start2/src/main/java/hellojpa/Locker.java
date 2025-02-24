package hellojpa;

import jakarta.persistence.*;

@Entity
@Table(name = "LOCKER")
public class Locker {

    @Id @GeneratedValue
    @Column(name = "LOCKER_ID")
    private Long Id;

    @Column(name = "NAME")
    private String name;
}
