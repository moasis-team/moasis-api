package site.moasis.api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.moasis.common.dto.GetNotificationListDTO;
import site.moasis.common.dto.SetNotificationListAsReadRequestDTO;
import site.moasis.common.entity.Notification;
import site.moasis.common.entity.Product;
import site.moasis.common.enums.NotificationType;
import site.moasis.common.repository.NotificationRepository;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final NotificationStatusKeyService notificationStatusKeyService;

    public Slice<GetNotificationListDTO> getNotificationList(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Slice<Notification> response = notificationRepository.findAllByOrderByCreatedAtDesc(pageable);

        return GetNotificationListDTO.fromSlice(response);
    }

    @Transactional
    public String[] setNotificationListAsRead(SetNotificationListAsReadRequestDTO readNotificationRequestDTO) {
        List<Notification> notificationList = Arrays.stream(readNotificationRequestDTO.getNotificationKeys()).map(notificationRepository::findByKey).toList();
        if (notificationList.get(0) == null) return new String[0];

        String[] notificationReadStatusKeyList = notificationList.stream().map(Notification::getNotificationReadStatusKey).toArray(String[]::new);
        Arrays.stream(notificationReadStatusKeyList).forEach(key -> notificationStatusKeyService.setNotificationStatus(key, true));

        return readNotificationRequestDTO.getNotificationKeys();
    }

    @Transactional
    public void createNotification(NotificationType notificationType, Product product, int changedAmount) {
        String notificationKey = UUID.randomUUID().toString();
        String notificationReadStatusKey = notificationStatusKeyService.createNotificationReadStatusKey();

        Notification notification = Notification.builder()
            .key(notificationKey)
            .productName(product.getName())
            .productNumber(product.getProductNumber())
            .type(notificationType)
            .remainingQuantity(product.getQuantity())
            .changedAmount(changedAmount)
            .notificationReadStatusKey(notificationReadStatusKey)
            .build();

        notificationRepository.save(notification);
    }

    public boolean existUnreadNotification() {
        return notificationStatusKeyService.existUnreadNotification();
    }
}
