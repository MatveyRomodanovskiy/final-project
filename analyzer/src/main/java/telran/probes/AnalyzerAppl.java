package telran.probes;

import java.util.function.Consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;

import lombok.RequiredArgsConstructor;
import telran.probes.dto.DeviationData;
import telran.probes.dto.ProbeData;
import telran.probes.dto.Range;
import telran.probes.service.RangeProviderClientService;

@SpringBootApplication
@RequiredArgsConstructor
public class AnalyzerAppl {
	String producerBindingName = "analyzerProducer-out-0";
	final RangeProviderClientService clientService;
	final StreamBridge streamBridge;
	public static void main(String[] args) {
		SpringApplication.run(AnalyzerAppl.class, args);

	}
	@Bean
	Consumer<ProbeData> analyzerConsumer() {
		return probeData -> probeDataAnalyzing(probeData);
	}
	private void probeDataAnalyzing(ProbeData probeData) {
		long id = probeData.id();
		double value = probeData.value();
		Range range = clientService.getRange(id);
		if (value > range.maxValue()) {
			sendDeviation(id, value, range.maxValue());
		} else if (value < range.minValue()){
			sendDeviation(id, value, range.minValue());
		}


	}
	private void sendDeviation(long id, double value, double limit) {
		DeviationData deviation = new DeviationData(id, value - limit, value, System.currentTimeMillis());
		streamBridge.send(producerBindingName, deviation);
	}

}