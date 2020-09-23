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

    @NotNull(message = "video não deve ser null")
    @JoinColumn(name = "id_video")
    @OneToOne
    private Video video;

    @NotNull(message = "pessoa não deve ser null")
    @JoinColumn(name = "id_pessoa")
    @OneToOne
    private Pessoa pessoa;

    private String texto;

    @Column(name = "dt_cria")
    private LocalDateTime dataCriacao;

    @Column(name = "dt_alter")
    private LocalDateTime dataAlteracao;
}
