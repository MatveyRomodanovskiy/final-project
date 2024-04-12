package telran.probes;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.boot.SpringApplication;
@SpringBootApplication
@ComponentScan(basePackages = {"telran"})
public class AccountProviderAppl {

	public static void main(String[] args) {
		SpringApplication.run(AccountProviderAppl.class, args);

	}

}
