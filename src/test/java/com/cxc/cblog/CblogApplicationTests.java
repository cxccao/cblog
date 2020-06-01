package com.cxc.cblog;

import com.cxc.cblog.entity.User;
import com.cxc.cblog.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class CblogApplicationTests {

    @Autowired
    UserService userService;

    @Test
    void contextLoads() {
        User user = userService.getById(1L);
        System.out.println(user.toString());
    }

}
