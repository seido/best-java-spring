package jp.seido.naoyuki.lib.entity;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "users")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @GeneratedValue
    @UuidGenerator
    @Id
    @Setter(lombok.AccessLevel.PACKAGE)
    private String id;
    private String username;
    private String password;
}
