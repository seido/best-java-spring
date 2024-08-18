package jp.seido.naoyuki.small_server.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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
        @NotBlank
        private String username;
        @NotBlank
        @Pattern(regexp = "^[\\p{Print}&&[^\\p{Cntrl}]]+$")
        private String password;
    }

    @PutMapping()
    public Respoonse putUser(@RequestBody @Valid PutRequest request) {
        User user = this.userStore.register(request.getUsername(), request.getPassword());

        logger.info("user: {}", user);
        return Respoonse.builder().user(user).build();
    }
}
