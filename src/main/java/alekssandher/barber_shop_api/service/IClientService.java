package alekssandher.barber_shop_api.service;

import alekssandher.barber_shop_api.entity.ClientEntity;
import alekssandher.barber_shop_api.exceptions.Exceptions.ConflictException;
import alekssandher.barber_shop_api.exceptions.Exceptions.NotFoundException;

public interface IClientService {
    ClientEntity save(final ClientEntity client) throws ConflictException;

    ClientEntity update(final ClientEntity client) throws ConflictException, NotFoundException;

    void delete(final long id) throws NotFoundException;

    
}
