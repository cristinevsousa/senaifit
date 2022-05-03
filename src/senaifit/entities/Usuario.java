package senaifit.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@MappedSuperclass
public abstract class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, length = 100)
    @NotEmpty
    private String nome;

    @OneToOne(cascade = CascadeType.ALL)
    private Endereco endereco;

    @Column(name = "tipo_usuario", nullable = false, length = 10)
    @Enumerated(value = EnumType.STRING)
    @NotNull
    private TipoUsuario tipoUsuario;

    public Usuario(TipoUsuario tipoUsuario) {
	this.tipoUsuario = tipoUsuario;
    }

}
