package senaifit.entities;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@Entity
public class Checkin {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Cliente cliente;

    @ManyToOne
    private Parceiro parceiro;

    @Column(name = "data_checkin", nullable = false, columnDefinition = "TIMESTAMP")
    @NotNull
    private LocalDateTime data;

    @ManyToOne
    private Atividade atividade;

    @Column(name = "tempo_atividade")
    @NotNull
    private int tempoAtividade;

    @Transient
    private FaixaEtaria faixaEtaria;

    public Checkin() {
	super();
	this.data = LocalDateTime.now();
    }

}
