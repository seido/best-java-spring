package jp.seido.naoyuki.small_server.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jp.seido.naoyuki.lib.entity.User;
import jp.seido.naoyuki.lib.service.UserStore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@RestController
@RequestMapping("/test")
@AllArgsConstructor
public class TestController {

    static final private Logger logger = LoggerFactory.getLogger(TestController.class);

    final private UserStore userStore;

    @Data
    @Builder
    public static class Respoonse {
        private User user;
    }

    @GetMapping()
    public Respoonse getUser(@RequestParam String id) {
        logger.info("id: {}", id);
        User user  = this.userStore.getUserById(id);
        logger.info("user: {}", user);
        
        return Respoonse.builder().user(user).build();
    }

    @Data
    private static class PutRequest {
        private String username;
        private String password;
    }

    @PutMapping()
    public Respoonse putUser(@RequestBody PutRequest request) {
        User user = this.userStore.register(request.getUsername(), request.getPassword());

        return Respoonse.builder().user(user).build();
    }
}
