package senaifit.controllers;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import senaifit.DTO.CheckinDTO;
import senaifit.entities.Checkin;
import senaifit.services.CheckinService;

@RestController
public class CheckinController {

    private CheckinService checkinService;

    public CheckinController(CheckinService checkinService) {
	this.checkinService = checkinService;
    }

    @PostMapping("/checkin/")
    public ResponseEntity<String> salvaCheckin(@Valid @RequestBody CheckinDTO checkin) {

	String msg = this.checkinService.salvaCheckin(checkin);

	if (msg.contains("não")) {
	    return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
	}
	return new ResponseEntity<>(msg, HttpStatus.CREATED);
    }

    @GetMapping("/checkin/{id}")
    private ResponseEntity<Checkin> obtemCheckin(@PathVariable String id) {

	return this.checkinService.obtemCheckin(Long.parseLong(id)).map(record -> ResponseEntity.ok().body(record))
		.orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/checkin/{id}")
    private ResponseEntity<String> deletaCheckinPorId(@PathVariable String id) {

	boolean retorno = this.checkinService.deletaCheckinPorId(Long.parseLong(id));
	if (retorno == true) {
	    return new ResponseEntity<>("Checkin removido com sucesso", HttpStatus.ACCEPTED);
	}
	return new ResponseEntity<>("Id não encontrado", HttpStatus.NOT_FOUND);
    }
}
