package site.moasis.moasisapi.common.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class SlackService {

    @Qualifier("slackClient")
    private final WebClient slackClient;

    public void sendAlert(String message) {
        String projectName = "MOASIS";
        String payload = """
                {
                    "blocks": [
                        {
                            "type": "header",
                            "text": {
                                "type": "plain_text",
                                "text": "%s",
                                "emoji": true
                            }
                        },
                        {
                            "type": "section",
                            "text": {
                                "type": "mrkdwn",
                                "text": "%s"
                            }
                        },
                        {
                            "type": "divider"
                        }
                    ]
                }
            """.formatted(projectName, message);

        slackClient.post()
            .bodyValue(payload)
            .retrieve()
            .toBodilessEntity()
            .block();
    }
}
