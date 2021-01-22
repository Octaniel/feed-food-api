package com.dro.feedfood.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * User: Octaniel
 * Date: 21/09/2020
 * Time: 17:59
 */
@Entity
@Table(name = "comentario")
@Getter
@Setter
@EqualsAndHashCode
public class Comentario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonBackReference("comentario_video")
    @NotNull(message = "video não deve ser null")
    @JoinColumn(name = "id_video")
    @ManyToOne
    private Video video;

    @JsonBackReference("comentario_pessoa")
    @NotNull(message = "pessoa não deve ser null")
    @JoinColumn(name = "id_pessoa")
    @ManyToOne
    private Pessoa pessoa;

    private String texto;

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
