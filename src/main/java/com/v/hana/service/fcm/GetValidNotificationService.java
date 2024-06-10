package com.v.hana.service.fcm;

import com.v.hana.dto.alarm.Notification;
import com.v.hana.service.RedisService;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class GetValidNotificationService {
    private final RedisService redisService;

    public List<Notification> getNotifications(String userId) {
        String key = "user:" + userId + ":notifications";
        List<Object> rawNotifications = redisService.right_pop_all(key);
        List<Notification> notifications = new ArrayList<>();

        for (Object rawNotification : rawNotifications) {
            String notificationStr = (String) rawNotification;
            String[] parts = notificationStr.split(", ");
            String title = parts[0].split(":")[1];
            String message = parts[1].split(":")[1];
            notifications.add(new Notification(title, message));
        }

        return notifications;
    }
}

