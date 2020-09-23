package com.dro.feedfood.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

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

    private String titulo;
    private String subTitulo;
    private String url;
    private String thumbnail;

    @Column(name = "permitir_comentarios")
    private Boolean permitirComentarios;

    @NotNull(message = "pessoa não deve ser null")
    @JoinColumn(name = "id_pessoa")
    @OneToOne
    private Pessoa pessoa;

    @Column(name = "dt_cria")
    private LocalDateTime dataCriacao;

    @Column(name = "dt_alter")
    private LocalDateTime dataAlteracao;

}
