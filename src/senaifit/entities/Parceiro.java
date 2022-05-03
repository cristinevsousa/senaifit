package senaifit.entities;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;

import lombok.Data;

@Data
@Entity
public class Parceiro extends Usuario {

    @Column(name = "data_cadastro", nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime dataCadastro;

    public Parceiro() {
	super(TipoUsuario.PARCEIRO);
    }
}
