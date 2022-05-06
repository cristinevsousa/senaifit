package senaifit.entities;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@MappedSuperclass
public abstract class Pessoa extends Usuario {

    @Column(length = 20)
    private String cpf;

    @Column(name = "data_nasc", nullable = false, columnDefinition = "DATE")
    @NotNull
    @Past
    private LocalDate dataNasc;

    @Column(nullable = false, length = 10)
    @Enumerated(value = EnumType.STRING)
    private Sexo sexo;

    @Column(nullable = false)
    private double peso;

    @Column(nullable = false)
    private double altura;

    public Pessoa(TipoUsuario tipoUsuario) {
	super(tipoUsuario);
    }
}
