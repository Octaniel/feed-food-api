package com.dro.feedfood.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * User: Octaniel
 * Date: 21/09/2020
 * Time: 17:50
 */

@Entity
@Table(name = "video")
@Getter
@Setter
@EqualsAndHashCode
public class Video {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String url;
    private String nome;
    private String descricao;
    private String igredientes;
    private String preparo;

    @Column(name = "canal_link")
    private String canalLink;

    @Column(name = "page_link")
    private String pageLink;

    @JsonBackReference("video_pessoa")
    @NotNull(message = "pessoa não deve ser null")
    @JoinColumn(name = "id_pessoa")
    @ManyToOne
    private Pessoa pessoa;

    @PreUpdate
    public void atualizar(){
        dataAlteracao = LocalDateTime.now();
    }

    @PrePersist
    public void salvar(){
        dataCriacao = LocalDateTime.now();
        dataAlteracao = LocalDateTime.now();
    }

    @Column(name = "dt_cria")
    private LocalDateTime dataCriacao;

    @Column(name = "dt_alter")
    private LocalDateTime dataAlteracao;

    @JsonManagedReference("gosto_video")
    @OneToMany(mappedBy = "video", cascade = CascadeType.ALL)
    private List<Gosto> gosto= new ArrayList<>();

    @JsonManagedReference("comentario_video")
    @OneToMany(mappedBy = "video", cascade = CascadeType.ALL)
    private List<Comentario> comentarios = new ArrayList<>();

    @Transient
    private List<Pessoa> listaDePessoasQueGostaram= new ArrayList<>();

    @JsonManagedReference("item_video")
    @OneToMany(mappedBy = "video", cascade = CascadeType.ALL)
    private List<Item> itens= new ArrayList<>();

}
