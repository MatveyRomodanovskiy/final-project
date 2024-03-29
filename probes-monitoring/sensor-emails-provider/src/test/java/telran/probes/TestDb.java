package telran.probes;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import telran.probes.model.*;
import telran.probes.repo.*;

@Component
@RequiredArgsConstructor
public class TestDb {
	final SensorEmailsRepo sensorEmailsProviderRepo;
	
	static final String URL_PATH = "http://localhost:8080/";
	
	static final long ID = 123;
	static final long ID_NOT_EXISTS = 124;
	static final long ID_EMAIL_NOT_EXISTS = 125;
	static final String[] SENSOR_EMAILS = {"123@456.com", "345@678.com", "222@333.com"};
	static final SensorEmailsDoc SENSOR_EMAILS_DOC = new SensorEmailsDoc(ID, SENSOR_EMAILS);
	static final SensorEmailsDoc SENSOR_EMAILS_NOT_EXISTI_DOC = new SensorEmailsDoc(ID_EMAIL_NOT_EXISTS, null);
	
	
	
	void createDb() {
		sensorEmailsProviderRepo.deleteAll();
		sensorEmailsProviderRepo.insert(SENSOR_EMAILS_DOC);
		sensorEmailsProviderRepo.insert(SENSOR_EMAILS_NOT_EXISTI_DOC);
	}

}
