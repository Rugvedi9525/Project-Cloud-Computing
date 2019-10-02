package edu.neu.coe.csye6225.cloudnativeapp.service;


import com.amazonaws.services.sns.AmazonSNSAsync;
import com.amazonaws.services.sns.AmazonSNSAsyncClientBuilder;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.*;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Service
@Slf4j
public class ResetPasswordService {


    AmazonSNSAsync amazonSNSClient;

    @PostConstruct
    public void initializeSNSClient() {

        this.amazonSNSClient = AmazonSNSAsyncClientBuilder.defaultClient();
    }


    public void sendMessage(String emailId) throws ExecutionException, InterruptedException {


        log.info("Sending Message --- {} ", emailId);

        //Future<CreateTopicResult> reset_password = amazonSNSClient.createTopicAsync("password_reset");
        //String topicArn = reset_password.get().getTopicArn();

        String topicArn = getTopicArn("password_reset");
        PublishRequest publishRequest = new PublishRequest(topicArn, emailId);
        Future<PublishResult> publishResultFuture = amazonSNSClient.publishAsync(publishRequest);
        String messageId = publishResultFuture.get().getMessageId();
        log.info("Send Message {} with message Id {} ", emailId, messageId);


    }

    public String getTopicArn(String topicName) {

        String topicArn = null;

        try {
            Topic topic = amazonSNSClient.listTopicsAsync().get().getTopics().stream()
                    .filter(t -> t.getTopicArn().contains(topicName))
                    .findAny()
                    .orElse(null);

            if (null != topic) {
                topicArn = topic.getTopicArn();
            } else {
                log.info("No Topic found by the name ---> ", topicName);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        log.info("Arn corresponding to topic name {} is {} ", topicName, topicArn);

        return topicArn;

    }

}
