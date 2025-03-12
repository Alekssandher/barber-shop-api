package alekssandher.barber_shop_api.dto.client;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ClientResponseDto(
    @JsonProperty("id")
    Long id,
    @JsonProperty("name")
    String name,
    @JsonProperty("email")
    String email,
    @JsonProperty("phone")
    String phone
) {}
