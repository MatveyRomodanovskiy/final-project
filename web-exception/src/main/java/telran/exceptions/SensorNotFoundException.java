package telran.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(value=HttpStatus.NOT_FOUND)
@SuppressWarnings("serial")
public class SensorNotFoundException extends NotFoundException{

	public SensorNotFoundException(long sensorId) {
		super(String.format("sensor %d not found", sensorId));
	}
}
