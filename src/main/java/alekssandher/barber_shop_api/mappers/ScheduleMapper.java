package alekssandher.barber_shop_api.mappers;

import alekssandher.barber_shop_api.dto.schedule.ScheduleRequestDto;
import alekssandher.barber_shop_api.dto.schedule.ScheduleResponseDto;
import alekssandher.barber_shop_api.entity.ClientEntity;
import alekssandher.barber_shop_api.entity.ScheduleEntity;


public class ScheduleMapper {
    public static ScheduleEntity toEntity(ScheduleRequestDto dto) {
        ClientEntity client = new ClientEntity();
        client.setId(dto.clientId()); 

        return new ScheduleEntity(
            dto.startAt(),
            dto.endAt(),
            client
        );
    }

    public static ScheduleResponseDto toResponseDto(ScheduleEntity entity)
    {
        return new ScheduleResponseDto(
            entity.getId(), 
            entity.getStartAt(), 
            entity.getEndAt(), 
            entity.getClient().getId()
        );
    }
}
