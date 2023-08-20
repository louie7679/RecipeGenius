package org.ascending.training.service;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.GetQueueUrlResult;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AmazonSQS sqs;

    private String queueName = "ascendingTraining";

    public void sendMessage(String messageBody, int delaySeconds) {
        SendMessageRequest send_msg_request = new SendMessageRequest()
                .withQueueUrl(getQueueUrl(queueName))
                .withMessageBody(messageBody)
                .withDelaySeconds(delaySeconds);
        sqs.sendMessage(send_msg_request);
    }

    private String getQueueUrl(String queueName) {
        GetQueueUrlResult getQueueUrlResult = sqs.getQueueUrl(queueName);
        return getQueueUrlResult.getQueueUrl();
    }
}
