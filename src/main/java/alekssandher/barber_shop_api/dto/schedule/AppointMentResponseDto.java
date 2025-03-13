package alekssandher.barber_shop_api.dto.schedule;

import java.util.List;

public record AppointMentResponseDto(
    int year,
    int month,
    List<ScheduleResponseDto> scheduledAppointments
) {}
