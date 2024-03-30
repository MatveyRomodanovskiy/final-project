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
import telran.probes.dto.SensorUpdateData;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailsProviderClientServiceImpl implements EmailsProviderClientService {
	final RestTemplate restTemplate;
	final ServiceConfiguration serviceConfiguration;
	HashMap<Long, String[]> sensorData = new HashMap<>();
	@Override
	public String[] getMails(long sensorId) {
		String[] strings = null;
		if (!sensorData.containsKey(sensorId)) {
			strings = serviceRequest(sensorId);
			if (!strings.equals(DEFAULT_EMAILS)) {
				sensorData.put(sensorId, strings);
				log.debug("add to map strings size: {} for sensor {}", strings.length, sensorId);
			}			
		}else {
			strings = sensorData.get(sensorId);
		}
		return strings;
	}

private String[] serviceRequest(long sensorId) {
	String[] strings = null;
ResponseEntity<?> responseEntity ;
try {
	responseEntity = restTemplate.exchange(getUrl(sensorId), HttpMethod.GET, null, String[].class);
	if (responseEntity.getStatusCode().isError()) {
		throw new Exception(responseEntity.getBody().toString());
	}
	strings = (String[]) responseEntity.getBody();
	log.debug("strings size {}", strings.length);
} catch (Exception e) {
	log.error("error at service request: {}", e.getMessage());
	strings = DEFAULT_EMAILS;
	log.warn("default strings value");
} 
return strings;

}
private String getUrl(long sensorId) {
String url = String.format("http://%s:%d%s%d",
		serviceConfiguration.getHost(),
		serviceConfiguration.getPort(),
		serviceConfiguration.getPath(),
		sensorId);
log.debug("url created is {}", url);
return url;
}

@Bean
Consumer<SensorUpdateData> updateEmailsConsumer() {
	return this::updateProcessing;
}

private void updateProcessing(SensorUpdateData updateData) {	
	if(updateData.emails()!= null && sensorData.containsKey(updateData.id())) {
		sensorData.put(updateData.id(), updateData.emails());
	}
}


}
