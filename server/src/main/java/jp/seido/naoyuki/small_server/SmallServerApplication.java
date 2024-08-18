package jp.seido.naoyuki.small_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"jp.seido.naoyuki.small_server", "jp.seido.naoyuki.lib"}) // Scan対象にライブラリのパッケージを含める
public class SmallServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmallServerApplication.class, args);
	}
}