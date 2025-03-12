package alekssandher.barber_shop_api.service.query.impl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Service;

import alekssandher.barber_shop_api.entity.ClientEntity;
import alekssandher.barber_shop_api.exceptions.Exceptions.ConflictException;
import alekssandher.barber_shop_api.exceptions.Exceptions.NotFoundException;
import alekssandher.barber_shop_api.repository.IClientRepository;
import alekssandher.barber_shop_api.service.query.IClientQueryService;

@Service
public class ClientQueryService implements IClientQueryService{

    private final IClientRepository repository;

    public ClientQueryService( IClientRepository repository)
    {
        this.repository = repository;
    }

    @Override
    public ClientEntity findById(long id) throws NotFoundException
    {
        
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Cliente não encontrado com o id: %s".formatted(id)));
        
    }

    @Override
    public List<ClientEntity> list() {
        return repository.findAll();
    }

    @Override
    public void verifyPhone(String phone) throws ConflictException {
        if(repository.existsByPhone(phone))
        {
            var message = "O telefone %s já está em uso".formatted(phone);
            throw new ConflictException(message);
        }

        return;
    }

    @Override
    public void verifyPhone(long id, String phone) throws ConflictException {

        Optional<ClientEntity> optional = repository.findByPhone(phone);

        if(optional.isPresent() && !Objects.equals(optional.get().getPhone(), phone))
        {
            var message = "O telefone %s já está em uso".formatted(phone);
            throw new ConflictException(message);
        }
    }

    @Override
    public void verifyEmail(String email) throws ConflictException {

        if(repository.existsByEmail(email))
        {
            var message = "O e-mail %s já está em uso.".formatted(email);
            throw new ConflictException(message);
        }
    }

    @Override
    public void verifyEmail(long id, String email) throws ConflictException {
        Optional<ClientEntity> optional = repository.findByEmail(email);

        if(optional.isPresent() && !Objects.equals(optional.get().getEmail(), email))
        {
            var message = "O e-mail %s já está em uso.".formatted(email);
            throw new ConflictException(message);
        }
    }
    
}
