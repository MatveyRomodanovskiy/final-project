package telran.probes;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.net.URI;

import telran.probes.dto.*;
import telran.probes.service.EmailsProviderClientService;
import org.junit.jupiter.api.*;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.stream.binder.test.*;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;



@SpringBootTest
@Import(TestChannelBinderConfiguration.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

class EmailsProviderServiceTest {

	private static final long SENSOR_ID = 123;
	private static final String[] EMAILS = {"100@200.com", "200@300.com", "300@400.com"};
	private static final String URL = "http://localhost:8282/range/sensor/";
	private static final String SENSOR_NOT_FOUND_MESSAGE ="sensor not found";
	private static final long SENSOR_ID_NOT_FOUND = 124;
	private static final long SENSOR_ID_NEW = 123;
	private static final long SENSOR_ID_UNAVAILABLE = 170;
	private static final String[] DEFAULT_EMAILS = EmailsProviderClientService.DEFAULT_EMAILS;
	private static final String[] EMAILS_UPDATED = {"100@200.com", "200@300.com", "300@400.com", "400@500.com" };
	
	@Autowired
InputDestination producer;
	@Autowired
EmailsProviderClientService providerService;
	@MockBean
RestTemplate restTemplate;
	private String updateBindingName = "updateEmailsConsumer-in-0";
	
	
	@Test
	@Order(1)
	void normalFlowNoCash() {
		ResponseEntity<String[]> responseEntity = new ResponseEntity<>(EMAILS, HttpStatus.OK);
		when(restTemplate.exchange(getUrl(SENSOR_ID), HttpMethod.GET, null, String[].class))
		.thenReturn(responseEntity );
		assertEquals(EMAILS, providerService.getMails(SENSOR_ID));
	}



	@SuppressWarnings("unchecked")
	@Test
	@Order(2)
	void normalFlowWithCash() {
		when(restTemplate.exchange(anyString(), any(HttpMethod.class), any(), any(Class.class)))
	.thenAnswer(new Answer<ResponseEntity<?>>() 
	
		{
		@Override
		public ResponseEntity<?> answer(InvocationOnMock invocation) throws Throwable {
			fail("method exchange should not be called");
			return null;
	}
	});
		assertEquals(EMAILS, providerService.getMails(SENSOR_ID));
	}
	
	
	@Test 
	@Order(3)
	void sensorNotFoundTest(){
		ResponseEntity<String> responseEntity = new ResponseEntity<>(SENSOR_NOT_FOUND_MESSAGE, HttpStatus.NOT_FOUND);
		when(restTemplate.exchange(getUrl(SENSOR_ID_NOT_FOUND), HttpMethod.GET, null, String.class))
		.thenReturn(responseEntity );
		assertEquals(DEFAULT_EMAILS, providerService.getMails(SENSOR_ID_NOT_FOUND));
		
	}
	
	@Test
	@Order(4)
	void defaultRangeNotInCache() {
		ResponseEntity<String[]> responseEntity = new ResponseEntity<>(EMAILS, HttpStatus.OK);
		when(restTemplate.exchange(getUrl(SENSOR_ID_NEW), HttpMethod.GET, null, String[].class))
		.thenReturn(responseEntity );
		assertEquals(EMAILS, providerService.getMails(SENSOR_ID_NEW));
	}
	
	@SuppressWarnings("unchecked")
	@Test
	void remoteWebServiceUnavailable() {
		when(restTemplate.exchange(anyString(), any(HttpMethod.class), any(), any(Class.class)))
		.thenThrow(new RestClientException("Service is unavailable"));
		assertEquals(DEFAULT_EMAILS, providerService.getMails(SENSOR_ID_UNAVAILABLE));
	}
	
	@Order(5)
	@Test
	void updateRangeSensorInMap() throws InterruptedException{
		producer.send(
				new GenericMessage<SensorUpdateData>(new SensorUpdateData(SENSOR_ID, null, EMAILS_UPDATED)
				 ), updateBindingName);
		Thread.sleep(100);
		assertEquals(EMAILS_UPDATED, providerService.getMails(SENSOR_ID));
	}
	
	private String getUrl(long sensorId) {
		
		return URL + SENSOR_ID;
	}

}


