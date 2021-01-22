package com.dro.feedfood.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "pessoa")
@Getter
@Setter
@EqualsAndHashCode
public class Pessoa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome é obrigatorio")
    private String nome;

    @NotBlank(message = "O apelido é obrigatorio")
    private String apelido;

    @NotBlank(message = "O email é obrigatorio")
    private String email;

    @NotNull(message = "pais é obrigatorio")
    private String pais;

    @NotNull(message = "Telemovel é obrigatorio")
    private String telemovel;

    private LocalDate dataNascimento;

    @JsonManagedReference("video_pessoa")
    @OneToMany(mappedBy = "pessoa", cascade = CascadeType.ALL)
    private List<Video> video;

    @JsonManagedReference("gosto_pessoa")
    @OneToMany(mappedBy = "pessoa", cascade = CascadeType.ALL)
    private List<Gosto> gostos;

    @JsonManagedReference("comentario_pessoa")
    @OneToMany(mappedBy = "pessoa", cascade = CascadeType.ALL)
    private List<Comentario> comentarios;

    @Column(name = "foto_url")
    private String fotoUrl;

    @Column(name = "dt_cria")
    private LocalDateTime dataCriacao;

    @Column(name = "dt_alter")
    private LocalDateTime dataAlteracao;


    @PreUpdate
    public void atualizar(){
        dataAlteracao = LocalDateTime.now();
    }

    @PrePersist
    public void salvar(){
        dataCriacao = LocalDateTime.now();
        dataAlteracao = LocalDateTime.now();
    }
}
