package telran.probes.dto;

import jakarta.validation.constraints.NotNull;
import static telran.probes.messages.ErrorMessages.*;

public record SensorEmails(@NotNull(message = MISSING_SENSOR_ID) long id, @NotNull(message = MISSING_EMAILS) String[]mails) {

}
