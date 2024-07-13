package site.moasis.common.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class SetNotificationListAsReadRequestDTO {
    @NotNull(message = "notificationKeys은 null일 수 없습니다.")
    @Size(min = 1, message = "notificationKeys은 최소한 하나의 요소를 가져야 합니다.")
    private String[] notificationKeys;
}
