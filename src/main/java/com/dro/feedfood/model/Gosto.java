package com.dro.feedfood.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * User: Octaniel
 * Date: 21/09/2020
 * Time: 18:00
 */
@Entity
@Table(name = "gosto")
@Getter
@Setter
@EqualsAndHashCode
public class Gosto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonBackReference("gosto_video")
    @JoinColumn(name = "id_video")
    @ManyToOne
    private Video video;

    @JsonBackReference("gosto_pessoa")
    @JoinColumn(name = "id_pessoa")
    @ManyToOne
    private Pessoa pessoa;
}
