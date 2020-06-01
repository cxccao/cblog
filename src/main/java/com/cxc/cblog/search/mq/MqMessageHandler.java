package com.cxc.cblog.search.mq;

import com.cxc.cblog.config.RabbitMqConfig;
import com.cxc.cblog.service.SearchService;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by cxc Cotter on 2020/5/31.
 */
@Component
@RabbitListener(queues = RabbitMqConfig.ES_QUEUE)
public class MqMessageHandler {
    @Autowired
    SearchService searchService;

    @RabbitHandler
    public void handler(PostMqIndexMessage postMqIndexMessage) {
        switch (postMqIndexMessage.getType()) {
            case PostMqIndexMessage.CREATE_OR_UPDATE:
                searchService.createOrUpdateIndex(postMqIndexMessage);
                break;
            case PostMqIndexMessage.REMOVE:
                searchService.removeIndex(postMqIndexMessage);
                break;
            default:
                break;
        }
    }
}
