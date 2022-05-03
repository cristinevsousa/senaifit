package senaifit.DTO;

import java.time.LocalDateTime;

import lombok.Data;
import senaifit.entities.TipoUsuario;
import senaifit.entities.Usuario;

@Data
public class ParceiroDTO extends Usuario {

    private LocalDateTime dataCadastro;

    public ParceiroDTO() {
	super(TipoUsuario.PARCEIRO);
    }

    public LocalDateTime getDataCadastro() {
	return dataCadastro;
    }

    public void setDataCadastro(LocalDateTime dataCadastro) {
	this.dataCadastro = dataCadastro;
    }

}
