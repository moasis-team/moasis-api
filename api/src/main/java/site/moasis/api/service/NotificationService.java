package site.moasis.api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.moasis.common.dto.NotificationDto;
import site.moasis.common.entity.Notification;
import site.moasis.common.entity.Product;
import site.moasis.common.enums.NotificationType;
import site.moasis.common.repository.NotificationRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private static final String UNREAD_NOTIFICATIONS_SET = "unread_notifications";
    private final RedisTemplate<String, Object> redisTemplate;

    public NotificationDto.GetResponse getNotifications(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Slice<Notification> notificationSlice = notificationRepository.findAllByOrderByCreatedAtDesc(pageable);

        return createGetNotificationListDTOFromSlice(notificationSlice);
    }

    private NotificationDto.GetResponse createGetNotificationListDTOFromSlice(Slice<Notification> notificationSlice) {
        return NotificationDto.GetResponse.builder()
            .slice(
                notificationSlice.map(
                    notification -> {
                        boolean isRead = getIsRead(notification.getKey());
                        return NotificationDto.GetDetails.builder()
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

    @Transactional
    public String[] readNotifications(NotificationDto.ReadRequest request) {
        List<Notification> notificationList = Arrays.stream(request.getKeys())
            .map(notificationRepository::findByKey)
            .filter(Objects::nonNull)
            .toList();

        if (notificationList.isEmpty()) {
            return new String[0];
        }

        String[] notificationIdArray = notificationList.stream()
            .map(Notification::getKey)
            .toArray(String[]::new);

        Arrays.stream(notificationIdArray).forEach(this::updateReadStatus);

        return request.getKeys();
    }

    @Transactional
    public void createNotification(NotificationType notificationType, Product product, int changedAmount) {
        String notificationKey = UUID.randomUUID().toString();

        Notification notification = Notification.builder()
            .key(notificationKey)
            .productName(product.getName())
            .productNumber(product.getProductNumber())
            .type(notificationType)
            .remainingQuantity(product.getQuantity())
            .changedAmount(changedAmount)
            .build();

        createReadStatus(notificationKey);
        notificationRepository.save(notification);
    }

    private void updateReadStatus(String notificationKey) {
        redisTemplate.opsForValue().set(notificationKey, Boolean.TRUE);
        redisTemplate.opsForSet().remove(UNREAD_NOTIFICATIONS_SET, notificationKey);
    }

    public boolean alarm() {
        Long size = redisTemplate.opsForSet().size(UNREAD_NOTIFICATIONS_SET);
        return size != null && size > 0;
    }

    private void createReadStatus(String notificationKey) {
        redisTemplate.opsForValue().set(notificationKey, Boolean.FALSE);
        redisTemplate.opsForSet().add(UNREAD_NOTIFICATIONS_SET, notificationKey);
    }

    public boolean getIsRead(String notificationKey) {
        Boolean isRead = (Boolean) redisTemplate.opsForValue().get(notificationKey);
        return Boolean.TRUE.equals(isRead);
    }
}
