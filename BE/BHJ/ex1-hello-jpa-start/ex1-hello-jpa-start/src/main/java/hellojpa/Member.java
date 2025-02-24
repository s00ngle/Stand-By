package hellojpa;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
public class Member {
    // PK 매핑
    @Id
    private Long id;

    // 데이터베이스 컬럼명이 name
    @Column(name = "name")
    private String username;

    // integer로 들어간다
    @Column(columnDefinition = "int default 0")
    private Integer age;

    // db에는 enum타입이 없는데 어떻게할까
    // 아래 어노테이션을 쓰면된다.
    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    // 데이터베이스는 date time timestamp 세가지가 존재
    // 타입을 그때그때 바꿔주면 된다.
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;

    // 아래와 같이 사용하면 각각 timestamp, date타입으로 변환해서
    // sql문으로 변경해 사용한다.
    private LocalDateTime localDateTime;
    private LocalDate localDate;

    // varchar를 넘어서는 큰 스트링
    @Lob
    private String description;

    // 그냥 자바내에서 변수로 사용. jpa가 관리하지 않음
    @Transient
    private int temp;


    public Member() {
    }

    public Member(Long id, String username, Integer age, RoleType roleType, Date createdDate, Date lastModifiedDate, String description, int temp) {
        this.id = id;
        this.username = username;
        this.age = age;
        this.roleType = roleType;
        this.createdDate = createdDate;
        this.lastModifiedDate = lastModifiedDate;
        this.description = description;
        this.temp = temp;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public RoleType getRoleType() {
        return roleType;
    }

    public void setRoleType(RoleType roleType) {
        this.roleType = roleType;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}