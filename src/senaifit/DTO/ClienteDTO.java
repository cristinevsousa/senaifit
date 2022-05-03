package senaifit.DTO;

import lombok.Data;
import senaifit.entities.TipoUsuario;

@Data
public class ClienteDTO extends PessoaDTO {

    public ClienteDTO() {
	super(TipoUsuario.CLIENTE);
    }
}
