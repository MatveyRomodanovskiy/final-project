package telran.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import telran.probes.messages.ErrorMessages;

@ResponseStatus(value=HttpStatus.NOT_FOUND, reason = ErrorMessages.SENSOR_NOT_EXISTS)
@SuppressWarnings("serial")
public class SensorNotFoundException extends NotFoundException{

	public SensorNotFoundException() {
		super(ErrorMessages.SENSOR_NOT_EXISTS);
	}
}
