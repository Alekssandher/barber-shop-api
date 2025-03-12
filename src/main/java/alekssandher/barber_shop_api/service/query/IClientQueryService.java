package alekssandher.barber_shop_api.service.query;

import java.util.List;

import alekssandher.barber_shop_api.entity.ClientEntity;
import alekssandher.barber_shop_api.exceptions.Exceptions.ConflictException;
import alekssandher.barber_shop_api.exceptions.Exceptions.NotFoundException;

public interface IClientQueryService {
    ClientEntity findById(final long id) throws NotFoundException;

    List<ClientEntity> list();

    void verifyPhone(final String phone) throws ConflictException;

    void verifyPhone(final long id, final String phone) throws ConflictException;

    void verifyEmail(final String email) throws ConflictException;

    void verifyEmail(final long id, final String email) throws ConflictException;
}
