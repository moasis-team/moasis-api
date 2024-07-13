package site.moasis.api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class NotificationStatusKeyService {
    private final RedisTemplate<String, Boolean> redisTemplate;

    public void setNotificationStatus(String notificationReadStatusKey, boolean isRead) {
        redisTemplate.opsForValue().set(notificationReadStatusKey, isRead);
    }

    public String createNotificationReadStatusKey() {
        Long sequence = redisTemplate.opsForValue().increment("notificationIsRead" + ":seq");
        return "notificationIsRead" + ":" + sequence;
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
}
