package jp.seido.naoyuki.lib.service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import jp.seido.naoyuki.lib.entity.User;

public class UserStoreMemory implements UserStore {

    private Map<String, User> users = new HashMap<>();
    
    public User register(String username, String password) {
        String id = UUID.randomUUID().toString();
        User user = User.builder().id(id).username(username).password(password).build();
        users.put(id, user);
        return user;
    }

    public User getUserById(String id) {
        return users.get(id);
    }
}
