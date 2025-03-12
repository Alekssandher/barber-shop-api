package alekssandher.barber_shop_api.service.impl;

import org.springframework.stereotype.Service;

import alekssandher.barber_shop_api.entity.ScheduleEntity;
import alekssandher.barber_shop_api.exceptions.Exceptions.ConflictException;
import alekssandher.barber_shop_api.exceptions.Exceptions.NotFoundException;
import alekssandher.barber_shop_api.repository.IScheduleRepository;
import alekssandher.barber_shop_api.service.IScheduleService;
import alekssandher.barber_shop_api.service.query.IScheduleQueryService;

@Service
public class ScheduleService implements IScheduleService{
    private final IScheduleRepository repository;
    private final IScheduleQueryService queryService;

    public ScheduleService(IScheduleRepository repository, IScheduleQueryService queryService)
    {
        this.repository = repository;
        this.queryService = queryService;
    }

    @Override
    public ScheduleEntity save(ScheduleEntity schedule) throws ConflictException 
    {
        queryService.verifyIfScheduleExists(schedule.getStartAt(), schedule.getEndAt());
        repository.save(schedule);
        return null;
    }

    @Override
    public void delete(long id) throws NotFoundException 
    {
        queryService.findById(id);
        repository.deleteById(id);

        return;
    }
}
