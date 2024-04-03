package telran.probes;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import telran.exceptions.NotFoundException;
import telran.probes.repo.SensorEmailsRepo;
import telran.probes.service.SensorEmailsProviderService;


@SpringBootTest
class SensorEmailsProviderServiceTests {
	@Autowired
	SensorEmailsProviderService sensorEmailsProviderService;
	@Autowired
	SensorEmailsRepo sensorRangeProviderRepo;
	@Autowired
	TestDb testDb;
	
	@BeforeEach
	void setUp() {
		testDb.createDb();
	}
	
	@Test
	void getSensorRange_correctFlow_success() {
		assertArrayEquals(TestDb.SENSOR_EMAILS, sensorEmailsProviderService.getSensorEmails(TestDb.ID));
	}
	
	@Test
	void getSensorRange_idNotExists_throwsException() {
		assertThrowsExactly(NotFoundException.class, () -> sensorEmailsProviderService.getSensorEmails(TestDb.ID_NOT_EXISTS));
	}

}
