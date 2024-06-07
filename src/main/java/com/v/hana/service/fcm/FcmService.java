package com.v.hana.service.fcm;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import com.v.hana.dto.alarm.FcmMessageDto;
import com.v.hana.dto.alarm.FcmSendDto;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FcmService {
    @Value("${fcm.google_application_credentials}")
    private String GOOGLE_APPLICATION_CREDENTIALS;

    @Value("${fcm.url}")
    private String FCM_URL;

    public int sendMessageTo(FcmSendDto fcmSendDto) throws IOException {
        String message = makeMessage(fcmSendDto);
        URL url = new URL(FCM_URL);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        String accessToken = getAccessToken();
        // Set the request method and headers
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setRequestProperty("Authorization", "Bearer " + accessToken);
        httpURLConnection.setRequestProperty("Content-Type", "application/json; UTF-8");
        httpURLConnection.setDoOutput(true);

        // Send the message
        try (OutputStream outputStream = httpURLConnection.getOutputStream()) {
            byte[] input = message.getBytes("utf-8");
            outputStream.write(input, 0, input.length);
        }

        int responseCode = httpURLConnection.getResponseCode();

        return responseCode == HttpURLConnection.HTTP_OK ? 1 : 0;
    }

    private String getAccessToken() throws IOException {
        GoogleCredentials googleCredentials =
                GoogleCredentials.fromStream(
                                new ClassPathResource(GOOGLE_APPLICATION_CREDENTIALS)
                                        .getInputStream())
                        .createScoped(
                                Arrays.asList(
                                        "https://www.googleapis.com/auth/firebase.messaging",
                                        "https://www.googleapis.com/auth/cloud-platform"));
        googleCredentials.refreshIfExpired();
        return googleCredentials.getAccessToken().getTokenValue();
    }

    private String makeMessage(FcmSendDto fcmSendDto) throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        FcmMessageDto fcmMessageDto =
                FcmMessageDto.builder()
                        .message(
                                FcmMessageDto.Message.builder()
                                        .token(fcmSendDto.getToken())
                                        .notification(
                                                FcmMessageDto.Notification.builder()
                                                        .title(fcmSendDto.getTitle())
                                                        .body(fcmSendDto.getBody())
                                                        .build())
                                        .build())
                        .validateOnly(false)
                        .build();

        return om.writeValueAsString(fcmMessageDto);
    }
}
