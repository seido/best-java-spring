package jp.seido.naoyuki.small_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"jp.seido.naoyuki.small_server", "jp.seido.naoyuki.lib"}) // Scan対象にライブラリのパッケージを含める
@EnableJpaRepositories({"jp.seido.naoyuki.lib.repository"}) // Applicationがあるパッケージ以外を使う場合はRepositoryを明示する必要がある。
@EntityScan({ "jp.seido.naoyuki.lib.entity" }) // Applicationがあるパッケージ以外を使う場合はEntityを明示する必要がある。
public class SmallServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmallServerApplication.class, args);
	}
}
