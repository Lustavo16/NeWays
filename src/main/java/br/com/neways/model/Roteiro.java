package br.com.neways.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Entity
@Getter
@Setter
public class Roteiro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String descricao;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "capa_id")
    private Arquivo capa;

    @Transient
    private MultipartFile arquivoCapa;

    @ManyToOne
    @JoinColumn(name = "criador_id")
    private Usuario criador;

    @OneToMany(mappedBy = "roteiro", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Destino> destinos = new ArrayList<>();

    //region Getters e Setters
    public String getCapaBase64() {
        if (this.capa == null || this.capa.getDados() == null) {
            return null;
        }
        return java.util.Base64.getEncoder().encodeToString(this.capa.getDados());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Usuario getCriador() {
        return criador;
    }

    public void setCriador(Usuario criador) {
        this.criador = criador;
    }

    public List<Destino> getDestinos() {
        return destinos;
    }

    public void setDestinos(List<Destino> destinos) {
        this.destinos = destinos;
    }

    public Arquivo getCapa() {
        return capa;
    }

    public void setCapa(Arquivo capa) {
        this.capa = capa;
    }

    public MultipartFile getArquivoCapa() {
        return arquivoCapa;
    }

    public void setArquivoCapa(MultipartFile arquivoCapa) {
        this.arquivoCapa = arquivoCapa;
    }

    //endregion
}
