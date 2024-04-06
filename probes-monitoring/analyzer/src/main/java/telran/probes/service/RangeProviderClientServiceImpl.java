package telran.probes.service;
import java.util.HashMap;
import java.util.function.Consumer;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import telran.probes.dto.Range;
import telran.probes.dto.SensorUpdateData;

@Service
@RequiredArgsConstructor
@Slf4j
public class RangeProviderClientServiceImpl implements RangeProviderClientService {
final RestTemplate restTemplate;
final ServiceConfiguration serviceConfiguration;
HashMap<Long, Range> sensorData = new HashMap<Long, Range>();
	
	
	@Override
	public Range getRange(long sensorId) {
		Range range = null;
		if (!sensorData.containsKey(sensorId)) {
			range = serviceRequest(sensorId);
			if (!range.equals(new Range(MIN_DEFAULT_VALUE, MAX_DEFAULT_VALUE))) {
				sensorData.put(sensorId, range);
			}			
		} else {
			range = sensorData.get(sensorId);
		}
		return range;
	}
	
	private Range serviceRequest(long sensorId) {
		Range range = null;
		ResponseEntity<?> responseEntity ;
		try {
			responseEntity = restTemplate.exchange(getUrl(sensorId), HttpMethod.GET, null, Range.class);
			if (responseEntity.getStatusCode().isError()) {
				throw new Exception(responseEntity.getBody().toString());
			}
			range = (Range) responseEntity.getBody();
			log.debug("range value {}", range);
		} catch (Exception e) {
			log.error("error at service request: {}", e.getMessage());
			range = new Range(MIN_DEFAULT_VALUE, MAX_DEFAULT_VALUE);
			log.warn("default range value: {}", range);
		} 
		return range;
		
	}
	private String getUrl(long sensorId) {
		String url = String.format("http://%s:%d%s/%d",
				serviceConfiguration.getHost(),
				serviceConfiguration.getPort(),
				serviceConfiguration.getPath(),
				sensorId);
		log.debug("url created is {}", url);
		return url;
	}
	
	
	@Bean
	Consumer<SensorUpdateData> updateRangeConsumer() {
		return this::updateProcessing;
	}

	private void updateProcessing(SensorUpdateData updateData) {	
		if(updateData.range()!= null && sensorData.containsKey(updateData.id())) {
			sensorData.put(updateData.id(), updateData.range());
		}
	}
}
