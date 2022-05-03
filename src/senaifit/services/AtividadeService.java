package senaifit.services;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import senaifit.entities.Atividade;
import senaifit.repositories.AtividadeRepository;

@Service
public class AtividadeService {

    @Autowired
    private AtividadeRepository atividadeRepo;

    public void salvaAtividade(@Valid Atividade atividade) {

	this.atividadeRepo.save(atividade);
    }

    public Optional<Atividade> obtemAtividade(long atividadeId) {

	return this.atividadeRepo.findById(atividadeId);
    }

    public Optional<Atividade> obtemAtividadePorNome(String nomeAtividade) {

	return this.atividadeRepo.findByNome(nomeAtividade);
    }

    public void deletaAtividade(Atividade atividade) {

	this.atividadeRepo.delete(atividade);
    }

    public void deletaAtividadePorId(long atividadeId) {

	this.atividadeRepo.deleteById(atividadeId);
    }
}
