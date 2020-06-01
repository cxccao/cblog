package com.cxc.cblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cxc.cblog.entity.UserMessage;
import com.cxc.cblog.service.UserMessageService;
import com.cxc.cblog.service.WebsocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


/**
 * Created by cxc Cotter on 2020/5/29.
 */
@Async
@Service
public class WebsocketServiceImpl implements WebsocketService {
    @Autowired
    UserMessageService userMessageService;

    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;

    @Override
    public void sendMessageCountToUser(Long toUserId) {
        int count = userMessageService.count(new QueryWrapper<UserMessage>().eq("to_user_id", toUserId).eq("status", 0));
        simpMessagingTemplate.convertAndSendToUser(toUserId.toString(), "/messCount", count);
    }
}
