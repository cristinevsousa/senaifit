package senaifit.entities;

import javax.persistence.Entity;

@Entity
public class Cliente extends Pessoa {

    public Cliente() {
	super(TipoUsuario.CLIENTE);
    }
}
