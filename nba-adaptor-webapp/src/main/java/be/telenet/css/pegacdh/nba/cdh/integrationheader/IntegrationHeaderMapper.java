package be.telenet.css.pegacdh.nba.cdh.integrationheader;

import be.telenet.css.lib.integrationheader.v2.IntegrationHeader;
import be.telenet.css.lib.integrationheader.v2.model.Application;
import be.telenet.css.lib.integrationheader.v2.model.MessageIdentification;
import be.telenet.css.lib.integrationheader.v2.model.MessageTimestamps;
import be.telenet.css.lib.integrationheader.v2.model.SystemInfo;
import com.google.gson.Gson;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

public class IntegrationHeaderMapper {

    public IntegrationHeader toDomain(Object feedbackBody, IntegrationHeaderProperties integrationHeaderProperties) {
        String uuidStr = UUID.randomUUID().toString();
        return IntegrationHeader.builder()
                .systemInfo(SystemInfo.builder()
                        .application(Application.builder()
                                .applicationName(integrationHeaderProperties.getApplicationName())
                                .build())
                        .environment(integrationHeaderProperties.getEnvironment())
                        .build())
                .messageIdentification(MessageIdentification.builder()
                        .correlationId(uuidStr)
                        .messageId(getMessageId(feedbackBody))
                        .requestId(uuidStr)
                        .build())
                .messageTimestamps(MessageTimestamps.builder()
                        .creationTimestamp(LocalDateTime.now())
                        .build())
                .build();
    }

    private static String getMessageId(Object feedbackBody) {
        byte[] feedBackInBytes = new Gson().toJson(feedbackBody).getBytes();
        Checksum checksum32 = new CRC32();
        checksum32.update(feedBackInBytes, 0, feedBackInBytes.length);
        return String.valueOf(checksum32.getValue());
    }

}

