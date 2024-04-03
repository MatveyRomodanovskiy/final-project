package telran.probes;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static telran.probes.UrlConstants.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import telran.exceptions.NotFoundException;
import telran.probes.exceptions.SensorNotFoundException;
import telran.probes.service.SensorRangeProviderService;
import static telran.probes.messages.ErrorMessages.*;

@WebMvcTest
class SensorRangeProviderControllerTests {
	@Autowired
	MockMvc mockMvc;
	@MockBean
	SensorRangeProviderService sensorRangeProviderService;
	@Autowired
	ObjectMapper mapper;
	String collectionNameRanges = "sensor_ranges";

	@Test
	void getSensorRange_correctFlow_success() throws Exception {
		when(sensorRangeProviderService.getSensorRange(TestDb.ID)).thenReturn(TestDb.RANGE);
		String expectedJson = mapper.writeValueAsString(TestDb.RANGE);
		String response = mockMvc.perform(get(TestDb.URL_PATH + SENSOR_RANGE_PATH + TestDb.ID)).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
		assertEquals(expectedJson, response);
	}
	
	@Test
	void getSensorRange_idNotExists_throwsException() throws Exception {
		when(sensorRangeProviderService.getSensorRange(TestDb.ID_NOT_EXISTS)).thenThrow(new SensorNotFoundException(TestDb.ID_NOT_EXISTS, collectionNameRanges));
		String response = mockMvc.perform(get(TestDb.URL_PATH + SENSOR_RANGE_PATH + TestDb.ID_NOT_EXISTS)).andExpect(status().isNotFound()).andReturn().getResolvedException().getMessage();
		assertEquals("sensor "+TestDb.ID_NOT_EXISTS+" not found in collection " + collectionNameRanges, response);
	}
	

}
