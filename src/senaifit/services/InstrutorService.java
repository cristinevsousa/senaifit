package senaifit.services;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import senaifit.entities.Instrutor;
import senaifit.repositories.InstrutorRepository;

@Service
public class InstrutorService {

    @Autowired
    private InstrutorRepository instrutorRepo;

    public void cadastraInstrutor(@Valid Instrutor instrutor) {

	this.instrutorRepo.save(instrutor);
    }

    public Optional<Instrutor> obtemInstrutor(long instrutorId) {

	return this.instrutorRepo.findById(instrutorId);
    }

    public void deletaInstrutorPorId(long id) {

	this.instrutorRepo.deleteById(id);
    }
}
