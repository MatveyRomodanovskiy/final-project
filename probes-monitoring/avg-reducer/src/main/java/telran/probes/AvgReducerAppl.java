package telran.probes;

import java.util.function.Consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import telran.probes.dto.ProbeData;
import telran.probes.service.AvgReducerService;
@Slf4j
@RequiredArgsConstructor
@SpringBootApplication
public class AvgReducerAppl {
	String producerBindingName = "avgReducerProducer-out-0";
	final StreamBridge streamBridge;
	final AvgReducerService reducerService;
	public static void main(String[] args) {
		SpringApplication.run(AvgReducerAppl.class, args);
	}

	@Bean
	Consumer<ProbeData> avgReducerConsumer (){
		return probeData -> probeDataReducinig(probeData);
	}

	private void probeDataReducinig(ProbeData probeData) {
		Double resDouble = reducerService.getAvgValue(probeData);
		if (resDouble != null) {
			ProbeData avgProbeData = new ProbeData(probeData.id(), resDouble, System.currentTimeMillis());
			streamBridge.send(producerBindingName, avgProbeData);
			log.debug("AvgReducer send new avgData:{} for sensor {}", resDouble, probeData.id());
		} else {
			log.debug("AvgReducer didn't send any for sensor",probeData.id());
		}
		
	}
}
