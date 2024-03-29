package telran.probes.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import telran.exceptions.SensorNotFoundException;
import telran.probes.model.SensorEmailsDoc;
import telran.probes.repo.SensorEmailsRepo;
@Service
@RequiredArgsConstructor
@Slf4j
public class SensorEmailsProviderServiceImpl implements SensorEmailsProviderService {
final SensorEmailsRepo sensorEmailsRepo;
	@Override
	public String[] getSensorEmails(long sensorId) {
		SensorEmailsDoc sensorEmailsDoc = sensorEmailsRepo.findById(sensorId)
				.orElseThrow(()->new SensorNotFoundException());
			log.debug("Sensor range for sensor with id {}recieved ", sensorId);
		return sensorEmailsDoc.getEmails();
	}

}
