package alekssandher.barber_shop_api.mappers;

import alekssandher.barber_shop_api.dto.client.ClientRequestDto;
import alekssandher.barber_shop_api.dto.client.ClientResponseDto;
import alekssandher.barber_shop_api.entity.ClientEntity;

public class ClientMapper {
    public static ClientEntity toEntity( ClientRequestDto dto)
    {
        return new ClientEntity(
            dto.name(),
            dto.email(),
            dto.phone()
        );
    }

    public static ClientResponseDto toResponseDto( ClientEntity entity )
    {
        return new ClientResponseDto(
            entity.getId(), 
            entity.getName(), 
            entity.getEmail(), 
            entity.getPhone()
        );
    }
}
