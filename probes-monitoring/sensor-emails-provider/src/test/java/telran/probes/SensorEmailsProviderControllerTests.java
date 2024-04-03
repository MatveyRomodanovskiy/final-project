package telran.probes;

import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static telran.probes.UrlConstants.*;
import static telran.probes.messages.ErrorMessages.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import telran.exceptions.NotFoundException;
import telran.probes.exceptions.SensorNotFoundException;
//import telran.exceptions.SensorNotFoundException;
import telran.probes.service.SensorEmailsProviderService;

@Slf4j
@WebMvcTest
class SensorEmailsProviderControllerTests {
	@Autowired
	MockMvc mockMvc;
	@MockBean
	SensorEmailsProviderService sensorEmailsProviderService;
	@Autowired
	ObjectMapper strMapper;
	

	@Test
	void getEmails_correctFlow_success() throws Exception {
		when(sensorEmailsProviderService.getSensorEmails(TestDb.ID)).thenReturn(TestDb.SENSOR_EMAILS);
		log.debug("size of emails array is {}", TestDb.SENSOR_EMAILS.length);
		String expectedJson = strMapper.writeValueAsString(TestDb.SENSOR_EMAILS);
		String response = mockMvc.perform(get(TestDb.URL_PATH + EMAILS_PATH + TestDb.ID)).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
		assertEquals(expectedJson, response);
	}
	
	@Test
	void getEmails_idNotExists_throwsException() throws Exception {
		when(sensorEmailsProviderService.getSensorEmails(TestDb.ID_NOT_EXISTS)).thenThrow(new SensorNotFoundException(TestDb.ID_NOT_EXISTS, "sensor_emails"));
		String response = mockMvc.perform(get(TestDb.URL_PATH + EMAILS_PATH + TestDb.ID_NOT_EXISTS)).andExpect(status().isNotFound()).andReturn().getResponse().getErrorMessage();
		assertEquals(SENSOR_NOT_EXISTS, response);
	}
	

}
