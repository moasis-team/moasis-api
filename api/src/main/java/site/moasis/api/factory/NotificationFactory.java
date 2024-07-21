package site.moasis.api.factory;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;
import site.moasis.api.service.NotificationReadStatusKeyService;
import site.moasis.common.dto.GetNotificationListDTO;
import site.moasis.common.entity.Notification;

@Component
@RequiredArgsConstructor
public class NotificationFactory {
    private final NotificationReadStatusKeyService notificationReadStatusKeyService;

    public GetNotificationListDTO createGetNotificationListDTOFromSlice(Slice<Notification> notificationSlice) {
        return GetNotificationListDTO.builder()
            .slice(
                notificationSlice.map(
                    notification -> {
                        boolean isRead = notificationReadStatusKeyService.getIsRead(notification.getKey());
                        return GetNotificationListDTO.GetNotificationListInnerDTO.builder()
                            .key(notification.getKey())
                            .type(notification.getType())
                            .productNumber(notification.getProductNumber())
                            .productName(notification.getProductName())
                            .remainingQuantity(notification.getRemainingQuantity())
                            .changedAmount(notification.getChangedAmount())
                            .isRead(isRead)
                            .build();
                    }
                )
            )
            .build();
    }
}
