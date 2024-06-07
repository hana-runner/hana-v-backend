package com.v.hana.service.fcm;

import com.v.hana.service.RedisService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetValidNotificationService {
    private final RedisService redisService;

    public List<Object> getNotifications(String userId) {
        String key = "user:" + userId + ":notifications";
        return redisService.right_pop_all(key);
    }
}
