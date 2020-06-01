package com.cxc.cblog.service;

/**
 * Created by cxc Cotter on 2020/5/29.
 */
public interface WebsocketService {
    void sendMessageCountToUser(Long toUserId);
}
