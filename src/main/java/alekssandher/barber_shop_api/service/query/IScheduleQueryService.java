package alekssandher.barber_shop_api.service.query;

import java.time.OffsetDateTime;
import java.util.List;

import alekssandher.barber_shop_api.entity.ScheduleEntity;
import alekssandher.barber_shop_api.exceptions.Exceptions.ConflictException;
import alekssandher.barber_shop_api.exceptions.Exceptions.NotFoundException;

public interface IScheduleQueryService {
    ScheduleEntity findById(final Long id) throws NotFoundException;

    List<ScheduleEntity> findInMonth(final OffsetDateTime startAt, final OffsetDateTime endAt) throws NotFoundException;

    void verifyIfScheduleExists(final OffsetDateTime startAt, final OffsetDateTime endAt) throws ConflictException;
}
