package jp.seido.naoyuki.lib.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import jp.seido.naoyuki.lib.entity.User;

public abstract class UserStoreTest {

    abstract UserStore createUserStore();
    
    @Test
    @DisplayName("ユーザーを登録できる")
    void testRegister() {
        UserStore userStore = createUserStore();
        User user1 = userStore.register("naoyuki", "password1");
        User user2 = userStore.register("takumi", "password2");

        User user1exp = userStore.getUserById(user1.getId());
        assertEquals(user1exp.getPassword(), "password1");

        User user2exp = userStore.getUserById(user2.getId());
        assertEquals(user2exp.getPassword(), "password2");
    }

    @Test
    @DisplayName("ユーザーが存在しない場合はnullを返す")
    void testGetUserByUsername() {
        UserStore userStore = createUserStore();
        User user = userStore.getUserById("dummy");
        assertNull(user);
    }

    @Test
    @DisplayName("ユーザーIDがユニークになっている")
    void testUniqueUsername() {
        UserStore userStore = createUserStore();
        User user1org = userStore.register("naoyuki", "password1");
        User user2org = userStore.register("takumi", "password2");
        User user1 = userStore.getUserById(user1org.getId());
        User user2 = userStore.getUserById(user2org.getId());
        assertNotNull(user1.getId());
        assertNotNull(user2.getId());
        assertNotEquals("", user1.getId());
        assertNotEquals("", user2.getId());
        assertNotEquals(user1.getId(), user2.getId());
    }
}
