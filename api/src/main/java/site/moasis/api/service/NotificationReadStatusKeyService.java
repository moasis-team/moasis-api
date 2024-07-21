package site.moasis.api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class NotificationReadStatusKeyService {
    private final RedisTemplate<String, Boolean> redisTemplate;

    public void setNotificationReadStatus(String notificationId, boolean isRead) {
        redisTemplate.opsForValue().set(notificationId, isRead);
    }

    public boolean existUnreadNotification() {
        Set<String> keys = redisTemplate.keys("*");
        if (keys == null || keys.isEmpty()) return false;

        for (String key : keys) {
            Boolean value = redisTemplate.opsForValue().get(key);
            if (value != null) return true;
        }
        return false;
    }

    public void createNotificationReadStatus(String notificationKey) {
        redisTemplate.opsForValue().set(notificationKey, Boolean.FALSE);
    }

    public boolean getIsRead(String notificationKey) {
        Boolean isRead = redisTemplate.opsForValue().get(notificationKey);
        if (isRead == null) return false;
        return isRead;
    }
}
