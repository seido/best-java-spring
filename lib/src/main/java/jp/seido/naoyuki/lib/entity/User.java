package jp.seido.naoyuki.lib.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Setter;

@Data
@Builder
@AllArgsConstructor
public class User {

    @Setter(lombok.AccessLevel.PACKAGE)
    private String id;
    private String username;
    private String password;
}
