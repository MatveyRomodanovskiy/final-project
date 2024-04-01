package telran.probes;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.stream.binder.test.InputDestination;
import org.springframework.cloud.stream.binder.test.OutputDestination;
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.messaging.support.GenericMessage;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.messaging.Message;

import telran.probes.dto.ProbeData;
import telran.probes.service.AvgReducerService;

@SpringBootTest
@Import(TestChannelBinderConfiguration.class)
class AvgReducerControllerTest {
	private static final long SENSOR_ID = 12;
	private static final double VALUE = 100;
	private static final ProbeData PROBE_DATA = new ProbeData(SENSOR_ID, VALUE, System.currentTimeMillis());
	private static final long SENSOR_ID_NO_AVG = 13;
	private static final double AVG_VALUE = 110;


	private static final ProbeData NO_AVG_DATA = new ProbeData(SENSOR_ID_NO_AVG, AVG_VALUE, System.currentTimeMillis());
	private static final long WAIT_TIME = 100;

	
	
	@MockBean
AvgReducerService avgReducerService;
	@Autowired
	InputDestination producer;
	@Autowired
	OutputDestination consumer;
	
	ObjectMapper mapper = new ObjectMapper();
	
	private String producerBindingName = "avgReducerProducer-out-0";
	private String consumerBindingName = "avgReducerConsumer-in-0";
	
	@BeforeEach
	void setServiceMock() {
		when(avgReducerService.getAvgValue(NO_AVG_DATA)).thenReturn(null);
		when(avgReducerService.getAvgValue(PROBE_DATA)).thenReturn(AVG_VALUE);
	}
	
	
	@Test
	void loadApplicationContext() {
		assertNotNull(avgReducerService);
	}
	
	@Test
	void noAvgTest() {
		producer.send(new GenericMessage<ProbeData>(NO_AVG_DATA), consumerBindingName);
		Message<byte[]> message = consumer.receive(WAIT_TIME, producerBindingName);
		assertNull(message);
	}
	
	@Test
	void withAvgTest() throws Exception {
		producer.send(new GenericMessage<ProbeData>(PROBE_DATA), consumerBindingName);
		Message<byte[]> message = consumer.receive(WAIT_TIME, producerBindingName);
		ProbeData resData = mapper.readValue(message.getPayload(), ProbeData.class);
		assertNotNull(message);
		assertEquals(resData.value(), AVG_VALUE);
		
	}

}
