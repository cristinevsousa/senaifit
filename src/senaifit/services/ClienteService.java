package senaifit.services;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import senaifit.entities.Cliente;
import senaifit.repositories.ClienteRepository;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepo;

    public void cadastraCliente(@Valid Cliente cliente) {

	this.clienteRepo.save(cliente);
    }

    public Optional<Cliente> obtemCliente(long clienteId) {

	return this.clienteRepo.findById(clienteId);
    }

    public void deletaClientePorId(long id) {

	this.clienteRepo.deleteById(id);
    }
}
