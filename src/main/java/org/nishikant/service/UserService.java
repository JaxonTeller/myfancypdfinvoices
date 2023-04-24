package org.nishikant.service;

import org.nishikant.model.User;

import java.util.UUID;

public class UserService {

    public User findById(String userId){
        //for now always return a user
        return new User(userId, UUID.randomUUID().toString());
    }
}
