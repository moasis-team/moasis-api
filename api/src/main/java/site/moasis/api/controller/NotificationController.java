package site.moasis.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.moasis.api.response.CommonResponse;
import site.moasis.api.service.NotificationService;
import site.moasis.common.dto.NotificationDto;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping
    public ResponseEntity<CommonResponse<NotificationDto.GetResponse>> getNotifications(
        @RequestParam("pageNumber") int pageNumber,
        @RequestParam("pageSize") int pageSize
    ) {
        NotificationDto.GetResponse response = notificationService.getNotifications(pageNumber, pageSize);
        return CommonResponse.success(response, "활동 알림 조회에 성공했습니다.");
    }

    @PostMapping("/read")
    public ResponseEntity<CommonResponse<String[]>> readNotifications(
        @RequestBody NotificationDto.ReadRequest request
    ) {
        String[] response = notificationService.readNotifications(request);
        return CommonResponse.success(response, "OK");
    }

    @GetMapping("/alarm")
    public ResponseEntity<CommonResponse<Boolean>> alarm() {
        boolean response = notificationService.alarm();
        return CommonResponse.success(response, "OK");
    }
}
