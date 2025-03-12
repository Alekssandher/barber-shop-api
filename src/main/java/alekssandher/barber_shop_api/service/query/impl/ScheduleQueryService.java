package alekssandher.barber_shop_api.service.query.impl;

import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import alekssandher.barber_shop_api.entity.ScheduleEntity;
import alekssandher.barber_shop_api.exceptions.Exceptions.ConflictException;
import alekssandher.barber_shop_api.exceptions.Exceptions.NotFoundException;
import alekssandher.barber_shop_api.repository.IScheduleRepository;
import alekssandher.barber_shop_api.service.query.IScheduleQueryService;

@Service
public class ScheduleQueryService implements IScheduleQueryService {

    private final IScheduleRepository repository;

    public ScheduleQueryService(IScheduleRepository repository)
    {
        this.repository = repository;
    }
    @Override
    public ScheduleEntity findById(Long id) throws NotFoundException {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Agenda não encontrada com id: %s".formatted(id)));
    }

    @Override
    public List<ScheduleEntity> findInMonth(OffsetDateTime startAt, OffsetDateTime endAt) throws NotFoundException {
        List<ScheduleEntity> schedules = repository.findByStartAtGreaterThanEqualAndEndAtLessThanEqualOrderByStartAtAscEndAtAsc(startAt, endAt);

        if (schedules.isEmpty()) {
            throw new NotFoundException("Agenda não encontrada no período selecionado");
        }

        return schedules;
    }

    @Override
    public void verifyIfScheduleExists(OffsetDateTime startAt, OffsetDateTime endAt) throws ConflictException {
        if(repository.existsByStartAtAndEndAt(startAt, endAt))
        {
            var message = "Já existe um cliente agendado no horário solicitado";

            throw new ConflictException(message);
        }
    }
    
}
