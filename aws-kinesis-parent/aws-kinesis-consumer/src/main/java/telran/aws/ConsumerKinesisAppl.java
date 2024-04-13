package telran.aws;

import java.util.function.Consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class ConsumerKinesisAppl {

	public static void main(String[] args) {
		SpringApplication.run(ConsumerKinesisAppl.class, args);

	}
	@Bean
	Consumer<String> consumerMessages() {
		return message -> log.info(message);
	}

}