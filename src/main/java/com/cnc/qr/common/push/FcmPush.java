package com.cnc.qr.common.push;

import com.cnc.qr.common.constants.CodeConstants.Flag;
import com.cnc.qr.common.dto.UnicastDto;
import com.cnc.qr.common.entity.MDevice;
import com.cnc.qr.common.repository.MDeviceRepository;
import com.cnc.qr.common.util.DateUtil;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.AndroidConfig;
import com.google.firebase.messaging.AndroidConfig.Priority;
import com.google.firebase.messaging.AndroidNotification;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import java.io.InputStream;
import java.time.ZonedDateTime;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


@Service
public class FcmPush {


    private static final Logger log = LoggerFactory.getLogger(FcmPush.class);

    /**
     * デバイスタリポジトリ.
     */
    @Autowired
    private MDeviceRepository deviceRepository;

    /**
     * PushTo店内用の端末.
     */
    @Async
    public void getPushToken(String storeId, String message) {

        List<MDevice> deviceList = deviceRepository
            .findByStoreIdAndDelFlag(storeId, Flag.OFF.getCode());

        for (MDevice device : deviceList) {
            UnicastDto unicastDto = new UnicastDto();
            unicastDto.setTokenId(device.getDeviceToken());
            unicastDto.setMessage(message);

            log.info("tokenId: " + unicastDto.getTokenId());
            this.sendMessage(unicastDto, 0);
        }
    }

    /**
     * プッシュメッセージ.
     */
    public void sendMessage(UnicastDto unicastDto, Integer idx) {

        try {
            // 4回以上の場合、そのまま。
            if (idx < 4) {

                log.info("プッシュ開始:" + DateUtil
                    .getZonedDateString(ZonedDateTime.now(), "yyyy-MM-dd HH:mm:ss"));

                // firebase情報取得
                InputStream stream = FcmPush.class.getClassLoader()
                    .getResourceAsStream("cncmessage-firebase.json");
                FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(stream))
                    .setDatabaseUrl("https://cncmessage.firebaseio.com")
                    .build();
                FirebaseApp defaultApp = null;
                try {
                    defaultApp = FirebaseApp.getInstance("[DEFAULT]");
                } catch (Exception ex) {
                    log.info("第一回:" + ex.getMessage());
                }

                if (defaultApp == null) {
                    FirebaseApp.initializeApp(options);
                }

                Message message = Message.builder()
                    .setAndroidConfig(AndroidConfig.builder()
                        .setPriority(Priority.HIGH)
                        .setNotification(AndroidNotification.builder()
                            .setTitle("QuickRestaurant")
                            .setBody(DateUtil
                                .getZonedDateString(ZonedDateTime.now(), "yyyy-MM-dd HH:mm:ss")
                                + "  " + unicastDto.getMessage())
                            .setSound("default")
                            .build())
                        .build())
                    .setToken(unicastDto.getTokenId())
                    .build();

                String response = FirebaseMessaging.getInstance().send(message);
                log.info("プッシュ終了:" + DateUtil
                    .getZonedDateString(ZonedDateTime.now(), "yyyy-MM-dd HH:mm:ss"));
                log.info("Successfully sent message: " + response);
            }
        } catch (Exception ex) {

            log.info("プッシュの異常のメッセージ：: " + ex.getMessage());
            idx = idx + 1;
            sendMessage(unicastDto, idx);
        }
    }
}
