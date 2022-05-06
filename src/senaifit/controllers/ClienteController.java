package senaifit.controllers;

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
    public ResponseEntity<String> salvaCliente(@Valid @RequestBody ClienteDTO cliente) {

	String msg = this.clienteService.cadastraCliente(cliente);

	return new ResponseEntity<>(msg, HttpStatus.CREATED);
    }

    @GetMapping("/cliente/{id}")
    public ResponseEntity<Cliente> obtemCliente(@PathVariable String id) {

	return this.clienteService.obtemCliente(Long.parseLong(id)).map(record -> ResponseEntity.ok().body(record))
		.orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/cliente/{id}")
    private ResponseEntity<String> deletaClientePorId(@PathVariable String id) {

	boolean retorno = this.clienteService.deletaClientePorId(Long.parseLong(id));
	if (retorno == true) {
	    return new ResponseEntity<>("Cliente removido com sucesso", HttpStatus.ACCEPTED);
	}
	return new ResponseEntity<>("Id não encontrado", HttpStatus.NOT_FOUND);
    }
}
