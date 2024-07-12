package site.moasis.common.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SlackAlertService {
    private final SlackService slackService;

    public void alertFailedProduct
        (String productName, String productNumber, String methodType) {
        String message = String.format("""
            [ Product Image %s Failed ]
            Product Name: `%s`
            Product Number: `%s`
            """, methodType, productName, productNumber);

        slackService.sendAlert(message);
    }
}
