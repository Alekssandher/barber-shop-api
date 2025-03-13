package alekssandher.barber_shop_api.dto.schedule;

import java.time.OffsetDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ScheduleRequestDto (
    @NotNull
    @Future(message = "A data de início deve estar no futuro")
    @JsonProperty("startAt")
    OffsetDateTime startAt,

    @NotNull
    @Future(message = "A data de término deve estar no futuro")
    @JsonProperty("endAt")
    OffsetDateTime endAt,

    @NotNull
    @Positive(message = "O ID do cliente deve ser um número positivo")
    @JsonProperty("clientId")
    Long clientId
){}
