package jp.seido.naoyuki.lib.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories({"jp.seido.naoyuki.lib.repository"}) // Applicationがあるパッケージ以外を使う場合はRepositoryを明示する必要がある。
@EntityScan({ "jp.seido.naoyuki.lib.entity" }) // Applicationがあるパッケージ以外を使う場合はEntityを明示する必要がある。
public class JpaConfig {

}
