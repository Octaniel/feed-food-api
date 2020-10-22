package com.dro.feedfood.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "item")
@Getter
@Setter
@EqualsAndHashCode
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "video não deve ser null")
    @JoinColumn(name = "id_video")
    @ManyToOne
    private Video video;

    private String nome;
}
