package com.cnc.qr.common.component;

import java.util.concurrent.TimeUnit;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * Redis排他ロック実現クラス.
 */
@Component
public class RedisLockUtil {

    /**
     * Redis操作クラス.
     */
    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 注文中Redisデータをロックする.
     *
     * @param key   Redis保存key
     * @param value 現在の時刻 + タイムスタンプ
     * @return ロック結果 trueの場合成功
     */
    public boolean lock(String key, String value) {

        // Nodejsと区別
        key = key + "lock";

        // keyを設定する
        if (redisTemplate.opsForValue().setIfAbsent(key, value)) {
            redisTemplate.expire(key, 60, TimeUnit.SECONDS);
            // 設定成功
            return true;
        }

        // ロックタイムアウトの決定-元の操作が異常になるのを防ぎ、ロック解除操作を実行しません
        String currentValue = redisTemplate.opsForValue().get(key);
        // ロックの期限切れ場合
        if (StringUtils.isNotEmpty(currentValue) && Long.parseLong(currentValue) < System
            .currentTimeMillis()) {
            // 最後のロックの時間値を取得する
            String oldValue = redisTemplate.opsForValue().getAndSet(key, value);
            // 並行性を防ぐ
            return StringUtils.isNotEmpty(oldValue) && oldValue.equals(currentValue);
        }
        return false;
    }


    /**
     * ロック解除.
     *
     * @param key   Redis保存key
     * @param value 現在の時刻 + タイムスタンプ
     */
    public void unlock(String key, String value) {
        key = key + "lock";
        String currentValue = redisTemplate.opsForValue().get(key);
        if (StringUtils.isNotEmpty(currentValue) && currentValue.equals(value)) {
            redisTemplate.opsForValue().getOperations().delete(key);
        }
    }
}
