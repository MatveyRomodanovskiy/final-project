package telran.probes.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import telran.exceptions.SensorNotFoundException;
import telran.probes.dto.Range;
import telran.probes.model.SensorRangeDoc;
import telran.probes.repo.SensorRangesRepo;
@Service
@RequiredArgsConstructor
@Slf4j
public class SensorRangeProviderServiceImpl implements SensorRangeProviderService {
final SensorRangesRepo sensorRangesRepo;
	@Override
	public Range getSensorRange(long sensorId) {
		SensorRangeDoc sensorRangeDoc = sensorRangesRepo.findById(sensorId)
				.orElseThrow(()->new SensorNotFoundException());
			log.debug("Sensor range for sensor with id {}recieved ", sensorId);
		return sensorRangeDoc.getRange();
	}

}
