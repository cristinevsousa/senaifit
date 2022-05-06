package senaifit.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import senaifit.DTO.InstrutorDTO;
import senaifit.entities.Instrutor;
import senaifit.repositories.InstrutorRepository;

@Service
public class InstrutorService {

    private InstrutorRepository instrutorRepo;

    public InstrutorService(InstrutorRepository instrutorRepo) {
	this.instrutorRepo = instrutorRepo;
    }

    public String cadastraInstrutor(InstrutorDTO instrutorDTO) {

	Instrutor instrutor = new Instrutor();
	instrutor.setEndereco(instrutorDTO.getEndereco());
	instrutor.setNome(instrutorDTO.getNome());
	instrutor.setNumRegistro(instrutorDTO.getNumRegistro());
	instrutor.setId(instrutorDTO.getId());
	instrutor.setAltura(instrutorDTO.getAltura());
	instrutor.setCpf(instrutorDTO.getCpf());
	instrutor.setDataNasc(instrutorDTO.getDataNasc());
	instrutor.setPeso(instrutorDTO.getPeso());
	instrutor.setSexo(instrutorDTO.getSexo());

	if (!(instrutor.getNome().isEmpty() && instrutor.equals(null))) {
	    this.instrutorRepo.save(instrutor);
	    return "Instrutor cadastrado com sucesso";
	}
	return "Erro ao cadastrar instrutor";

    }

    public Optional<Instrutor> obtemInstrutor(long instrutorId) {

	Optional<Instrutor> instrutor = this.instrutorRepo.findById(instrutorId);

	if (instrutor.isPresent()) {
	    return instrutor;
	}
	return Optional.empty();
    }

    public boolean deletaInstrutorPorId(long instrutorId) {

	Optional<Instrutor> instrutor = obtemInstrutor(instrutorId);
	if (instrutor.isPresent()) {
	    this.instrutorRepo.deleteById(instrutorId);
	    return true;
	}
	return false;
    }
}
