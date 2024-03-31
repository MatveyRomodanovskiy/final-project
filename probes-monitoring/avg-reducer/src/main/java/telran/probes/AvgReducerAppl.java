package telran.probes;

import java.util.function.Consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;

import lombok.RequiredArgsConstructor;
import telran.probes.dto.ProbeData;
import telran.probes.service.AvgReducerService;
@RequiredArgsConstructor
@SpringBootApplication
public class AvgReducerAppl {
	public static void main(String[] args) {
		String producerBindingName = "avgReducerProducer-out-0";
		final StreamBridge streamBridge;
		final AvgReducerService avgReducerService;
		SpringApplication.run(AvgReducerAppl.class, args);
	}

	@Bean
	Consumer<ProbeData> avgReducerConsumer (){
		return probeData -> probeDataReducinig(probeData);
	}

	private void probeDataReducinig(ProbeData probeData) {
		Double resDouble = 
		
	}
}
