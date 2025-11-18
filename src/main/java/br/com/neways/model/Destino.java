package br.com.neways.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Destino {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private String nome;

    private String localizacao;

    private String descricao;

    private String observacao;

    private byte[] foto;

    @ManyToOne
    @JoinColumn(name = "roteiro_id")
    private Roteiro roteiro;
}
