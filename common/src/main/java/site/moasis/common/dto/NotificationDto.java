package site.moasis.common.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.domain.Slice;
import site.moasis.common.enums.NotificationType;

public class NotificationDto {

    @Builder
    @Getter
    @ToString
    public static class GetResponse {
        private Slice<GetDetails> slice;
    }

    @Builder
    @Getter
    @ToString
    public static class GetDetails {
        private String key;
        private NotificationType type;
        private String productNumber;
        private String productName;
        private int remainingQuantity;
        private int changedAmount;
        private boolean isRead;
    }

    @Getter
    @ToString
    public static class ReadRequest {
        @NotNull(message = "notificationKeys은 null일 수 없습니다.")
        @Size(min = 1, message = "notificationKeys은 최소한 하나의 요소를 가져야 합니다.")
        private String[] keys;
    }
}
