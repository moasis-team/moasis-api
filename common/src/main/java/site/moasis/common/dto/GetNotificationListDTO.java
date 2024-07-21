package site.moasis.common.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Slice;
import site.moasis.common.enums.NotificationType;

@Builder
@Getter
public class GetNotificationListDTO {
    private Slice<GetNotificationListInnerDTO> slice;

    @Builder
    @Getter
    public static class GetNotificationListInnerDTO {
        private String key;
        private NotificationType type;
        private String productNumber;
        private String productName;
        private int remainingQuantity;
        private int changedAmount;
        private boolean isRead;
    }
}
