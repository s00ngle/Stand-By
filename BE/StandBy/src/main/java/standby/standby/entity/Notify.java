package standby.standby.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.domain.Auditable;
import org.springframework.data.jpa.domain.AbstractAuditable;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@Getter
@Setter
public class Notify {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    private Long id;

    private String content;
   /*@Embedded
   private NotificationContent content;*/

    //@Embedded
    //private RelatedURL url;
    private String url;
    @Column(nullable = false)
    private Boolean isRead;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NotificationType notificationType;

    @ManyToOne
    @JoinColumn(name = "member_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User receiver;

    @Builder
    public Notify(User receiver, NotificationType notificationType, String content, String url, Boolean isRead) {
        this.receiver = receiver;
        this.notificationType = notificationType;
        this.content = content;
        this.url = url;
        this.isRead = isRead;
    }
    public Notify() {
    }
    public enum NotificationType{
        YATA, REVIEW, CHAT
    }
}