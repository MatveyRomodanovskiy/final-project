package telran.aws;

import java.util.function.Supplier;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class ProducerTestAppl {

	private static final long TIMEOUT = 10000;
	int count = 1;
	public static void main(String[] args) throws InterruptedException {
		ConfigurableApplicationContext ctx=SpringApplication.run(ProducerTestAppl.class, args);
		Thread.sleep(TIMEOUT);
		ctx.close();

	}

	
	
	@Bean
	Supplier<String> supplierMessages(){
		return () -> "message " + count++;
	}

}
