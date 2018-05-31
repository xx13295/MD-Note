package plus.ojbk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"plus.ojbk.mongo.*"})
public class MongoBdApplication {

	public static void main(String[] args) {
		SpringApplication.run(MongoBdApplication.class, args);
	}
}
