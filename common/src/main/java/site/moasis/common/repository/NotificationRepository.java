package site.moasis.common.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import site.moasis.common.entity.Notification;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    Notification findByKey(String key);

    Slice<Notification> findAllByOrderByCreatedAtDesc(Pageable pageable);
}
