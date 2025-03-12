package alekssandher.barber_shop_api.service.impl;

import org.springframework.stereotype.Service;

import alekssandher.barber_shop_api.entity.ClientEntity;
import alekssandher.barber_shop_api.exceptions.Exceptions.ConflictException;
import alekssandher.barber_shop_api.exceptions.Exceptions.NotFoundException;
import alekssandher.barber_shop_api.repository.IClientRepository;
import alekssandher.barber_shop_api.service.IClientService;
import alekssandher.barber_shop_api.service.query.IClientQueryService;

@Service
public class ClientService implements IClientService{
    
    private final IClientRepository repository;
    private final IClientQueryService queryService;

    public ClientService(IClientRepository repository, IClientQueryService queryService)
    {
        this.repository = repository;
        this.queryService = queryService;
    }

    @Override
    public ClientEntity save(ClientEntity client) throws ConflictException {
        queryService.verifyEmail(client.getEmail());
        queryService.verifyPhone(client.getPhone());

        return repository.save(client);
    }

    @Override
    public ClientEntity update(ClientEntity client) throws ConflictException, NotFoundException {
        queryService.verifyEmail(client.getId(), client.getEmail());
        queryService.verifyPhone(client.getId(), client.getPhone());

        var stored = queryService.findById(client.getId());

        stored.setEmail(client.getEmail());
        stored.setPhone(client.getPhone());
        stored.setName(client.getName());

        return repository.save(stored);
    }

    @Override
    public void delete(long id) throws NotFoundException {
        queryService.findById(id);
        repository.deleteById(id);

        return;
    }


}
