package telran.probes.dto;


import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import static telran.probes.messages.ErrorMessages.*;
public record SensorRange(@NotNull(message = MISSING_SENSOR_ID) long id, @NotNull(message = MISSING_RANGE) @Valid Range range) {

}
