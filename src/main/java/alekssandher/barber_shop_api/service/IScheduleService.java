package alekssandher.barber_shop_api.service;

import alekssandher.barber_shop_api.entity.ScheduleEntity;
import alekssandher.barber_shop_api.exceptions.Exceptions.ConflictException;
import alekssandher.barber_shop_api.exceptions.Exceptions.NotFoundException;

public interface IScheduleService {
    ScheduleEntity save(final ScheduleEntity schedule) throws ConflictException;

    void delete(final long id) throws NotFoundException;
}
