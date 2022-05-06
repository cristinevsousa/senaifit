package senaifit.entities;

import javax.persistence.Column;
import javax.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
public class Instrutor extends Pessoa {

    @Column(name = "num_registro", nullable = false)
    private String numRegistro;

    public Instrutor() {
	super(TipoUsuario.INSTRUTOR);
    }

}
