package senaifit.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import senaifit.DTO.ClienteDTO;
import senaifit.entities.Cliente;
import senaifit.repositories.ClienteRepository;

@Service
public class ClienteService {

    private ClienteRepository clienteRepo;

    public ClienteService(ClienteRepository clienteRepo) {
	this.clienteRepo = clienteRepo;
    }

    public String cadastraCliente(ClienteDTO clienteDTO) {

	Cliente cliente = new Cliente();
	cliente.setAltura(clienteDTO.getAltura());
	cliente.setCpf(clienteDTO.getCpf());
	cliente.setDataNasc(clienteDTO.getDataNasc());
	cliente.setEndereco(clienteDTO.getEndereco());
	cliente.setId(clienteDTO.getId());
	cliente.setNome(clienteDTO.getNome());
	cliente.setPeso(clienteDTO.getPeso());
	cliente.setSexo(clienteDTO.getSexo());

	if (!(cliente.getNome().isEmpty() && cliente.equals(null))) {
	    this.clienteRepo.save(cliente);
	    return "Cliente cadastrado com sucesso!";
	}
	return "Erro ao cadastrar cliente";
    }

    public Optional<Cliente> obtemCliente(long clienteId) {

	Optional<Cliente> cliente = this.clienteRepo.findById(clienteId);

	if (cliente.isPresent()) {
	    return cliente;
	}
	return Optional.empty();
    }

    public boolean deletaClientePorId(long clienteId) {

	Optional<Cliente> cliente = obtemCliente(clienteId);
	if (cliente.isPresent()) {
	    this.clienteRepo.deleteById(clienteId);
	    return true;
	}
	return false;
    }
}
