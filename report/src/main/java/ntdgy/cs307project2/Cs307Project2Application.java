package ntdgy.cs307project2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import springfox.documentation.oas.annotations.EnableOpenApi;

@EnableAsync
@SpringBootApplication
@EnableOpenApi
public class Cs307Project2Application {
	public static void main(String[] args) {
		SpringApplication.run(Cs307Project2Application.class, args);
	}

}
