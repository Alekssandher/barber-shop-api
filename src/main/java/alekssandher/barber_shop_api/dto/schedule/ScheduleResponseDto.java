package alekssandher.barber_shop_api.dto.schedule;

import java.time.OffsetDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ScheduleResponseDto (
    @JsonProperty("id")
    Long id,
    @JsonProperty("startAt")
    OffsetDateTime startAt,
    @JsonProperty("endAt")
    OffsetDateTime endAt,
    @JsonProperty("clientId")
    Long clientId
){}
