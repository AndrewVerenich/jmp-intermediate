package com.adnver.protoclbuffers.controller;

import com.adnver.protoclbuffers.model.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SimpleController
{
    @GetMapping("/user")
    public User.UserProto getUser()
    {
        return User.UserProto.newBuilder()
                .setUserId("300000001088254")
                .setUserName("Andrey")
                .setAge(30)
                .build();
    }
}
