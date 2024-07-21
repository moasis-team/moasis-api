package site.moasis.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.moasis.api.response.CommonResponse;
import site.moasis.api.service.NotificationService;
import site.moasis.common.dto.GetNotificationListDTO;
import site.moasis.common.dto.SetNotificationListAsReadRequestDTO;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping
    public ResponseEntity<CommonResponse<GetNotificationListDTO>> getNotificationList(
        @RequestParam("pageNumber") int pageNumber,
        @RequestParam("pageSize") int pageSize
    ) {
        GetNotificationListDTO response = notificationService.getNotificationList(pageNumber, pageSize);
        return CommonResponse.success(response, "활동 알림 조회에 성공했습니다.");
    }

    @PostMapping("/read")
    public ResponseEntity<CommonResponse<String[]>> setNotificationListAsRead(
        @RequestBody SetNotificationListAsReadRequestDTO setNotificationListAsReadRequestDTO
    ) {
        String[] response = notificationService.setNotificationListAsRead(setNotificationListAsReadRequestDTO);
        return CommonResponse.success(response, "OK");
    }

    @GetMapping("/exist-unread")
    public ResponseEntity<CommonResponse<Boolean>> existUnReadNotification() {
        boolean response = notificationService.existUnreadNotification();
        return CommonResponse.success(response, "OK");
    }
}
