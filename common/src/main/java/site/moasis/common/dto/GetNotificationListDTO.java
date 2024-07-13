package site.moasis.common.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Slice;
import site.moasis.common.entity.Notification;
import site.moasis.common.enums.NotificationType;

@Builder
@Getter
public class GetNotificationListDTO {
    private String key;
    private NotificationType type;
    private String productNumber;
    private String productName;
    private int remainingQuantity;
    private int changedAmount;

    public static Slice<GetNotificationListDTO> fromSlice(Slice<Notification> notificationList) {
        return notificationList.map(notification -> GetNotificationListDTO.builder()
            .key(notification.getKey())
            .type(notification.getType())
            .productNumber(notification.getProductNumber())
            .productName(notification.getProductName())
            .remainingQuantity(notification.getRemainingQuantity())
            .changedAmount(notification.getChangedAmount())
            .build()
        );
    }
}
