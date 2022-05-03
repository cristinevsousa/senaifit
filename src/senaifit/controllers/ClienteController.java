package senaifit.controllers;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import senaifit.DTO.ClienteDTO;
import senaifit.entities.Cliente;
import senaifit.services.ClienteService;

@RestController
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @PostMapping("/cliente/")
    public ResponseEntity<String> salvaCliente(@Valid @RequestBody ClienteDTO clienteDTO) {

	Cliente cliente = new Cliente();
	cliente.setAltura(clienteDTO.getAltura());
	cliente.setCpf(clienteDTO.getCpf());
	cliente.setDataNasc(clienteDTO.getDataNasc());
	cliente.setEndereco(clienteDTO.getEndereco());
	cliente.setId(clienteDTO.getId());
	cliente.setNome(clienteDTO.getNome());
	cliente.setPeso(clienteDTO.getPeso());
	cliente.setSexo(clienteDTO.getSexo());

	this.clienteService.cadastraCliente(cliente);

	return new ResponseEntity<>("Cliente cadastrado com sucesso!", HttpStatus.CREATED);
    }

    @GetMapping("/cliente/{id}")
    public ResponseEntity<Cliente> obtemCliente(@PathVariable String id) {

	Optional<Cliente> cliente = this.clienteService.obtemCliente(Long.parseLong(id));

	return ResponseEntity.of(cliente);
    }

    @DeleteMapping("/cliente/remove/{id}")
    private ResponseEntity<String> deletaClientePorId(@PathVariable String id) {

	this.clienteService.deletaClientePorId(Long.parseLong(id));

	return new ResponseEntity<>("Cliente removido com sucesso", HttpStatus.ACCEPTED);
    }
}
