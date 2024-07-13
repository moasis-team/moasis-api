package site.moasis.common.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.moasis.common.enums.NotificationType;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = {@Index(name = "notification_key_index", columnList = "notification_read_status_key")})
public class Notification extends BaseEntity {
    @Column(unique = true)
    private String key;

    private String productNumber;

    private String productName;

    private NotificationType type;

    private int remainingQuantity = 0;

    private int changedAmount = 0;

    @Column(name = "notification_read_status_key", unique = true)
    private String notificationReadStatusKey;
}
