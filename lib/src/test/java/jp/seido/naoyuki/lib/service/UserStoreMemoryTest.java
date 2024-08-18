package jp.seido.naoyuki.lib.service;

public class UserStoreMemoryTest extends UserStoreTest {

    @Override
    UserStore createUserStore() {
        return new UserStoreMemory();
    }
}
