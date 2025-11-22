package br.com.neways.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;

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

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "foto_id")
    private Arquivo foto;

    @Transient
    private MultipartFile arquivoDestino;

    @ManyToOne
    @JoinColumn(name = "roteiro_id")
    private Roteiro roteiro;

    //region Getters e Setters
    public String getImagemBase64() {
        if (this.foto == null || this.foto.getDados() == null) {
            return null;
        }
        return Base64.getEncoder().encodeToString(this.foto.getDados());
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Arquivo getFoto() {
        return foto;
    }

    public void setFoto(Arquivo foto) {
        this.foto = foto;
    }

    public Roteiro getRoteiro() {
        return roteiro;
    }

    public void setRoteiro(Roteiro roteiro) {
        this.roteiro = roteiro;
    }

    public MultipartFile getArquivoDestino() {
        return arquivoDestino;
    }

    public void setArquivoDestino(MultipartFile arquivoDestino) {
        this.arquivoDestino = arquivoDestino;
    }

    //endregion
}
