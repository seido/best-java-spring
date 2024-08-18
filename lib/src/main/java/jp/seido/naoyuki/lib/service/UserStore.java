package jp.seido.naoyuki.lib.service;

import jp.seido.naoyuki.lib.entity.User;

public interface UserStore {

    public User register(String username, String password);

    public User getUserById(String id);
}
